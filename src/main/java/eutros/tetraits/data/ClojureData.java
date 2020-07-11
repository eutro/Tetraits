package eutros.tetraits.data;

import clojure.lang.Compiler;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;
import eutros.tetraits.Tetraits;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public abstract class ClojureData implements FileVisitor<Path> {

    public final Map<ResourceLocation, IFn> data = new HashMap<>();

    public IFn get(ResourceLocation location) {
        return data.get(location);
    }

    protected abstract String getPath();

    private final ResourceLocation location = new ResourceLocation(Tetraits.MOD_ID, getPath());

    public ResourceLocation getType() {
        return location;
    }

    public void load() {
        data.clear();

        try {
            Files.walkFileTree(getDataRoot(), this);
        } catch(IOException ignored) {
            // This shouldn't happen.
        }
    }

    protected Path getDataRoot() {
        return (server == null ?
                Minecraft.getInstance().gameDir :
                server.getDataDirectory())
                .toPath()
                .resolve(Tetraits.MOD_ID)
                .resolve(getPath());
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        Path root = getDataRoot();
        if(root.getParent().equals(dir.getParent()) &&
                !dir.equals(root)) {
            return FileVisitResult.SKIP_SUBTREE;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if(!FilenameUtils.getExtension(file.getFileName().toString()).equals("clj")) {
            return FileVisitResult.CONTINUE;
        }

        Path relative = getDataRoot().relativize(file);

        if(relative.getNameCount() < 2) {
            Tetraits.LOGGER.warn("File: \"{}\" should be in name-spaced subfolder!", file);
            return FileVisitResult.CONTINUE;
        }

        ResourceLocation rl = new ResourceLocation(relative.getName(0).toString(),
                FilenameUtils.removeExtension(
                        relative.subpath(1, relative.getNameCount())
                                .toString()));

        try {
            InputStreamReader rdr = new InputStreamReader(Files.newInputStream(file));
            getExecutor().runImmediately(() -> {
                try {
                    Object loaded = Compiler.load(
                            rdr,
                            file.toAbsolutePath()
                                    .normalize()
                                    .toString(),
                            rl.toString()
                    );
                    if(loaded instanceof IFn) {
                        data.put(rl, (IFn) loaded);
                        Tetraits.LOGGER.debug("Loaded {}.", rl);
                    } else {
                        Tetraits.LOGGER.error("{} didn't return IFn, should be a function.", rl);
                    }
                } catch(Compiler.CompilerException compilerError) {
                    IPersistentMap data = compilerError.getData();
                    Tetraits.LOGGER.error(
                            "Couldn't compile \"{}\".\n" +
                                    "   Error at line: {}\n" +
                                    "          column: {}\n" +
                                    "   {}\n" +
                                    "   Caused by: {}",
                            rl,
                            compilerError.line,
                            data.valAt(Compiler.CompilerException.ERR_COLUMN),
                            compilerError.toString(),
                            compilerError.getCause());
                }
            });
        } catch(IOException e) {
            Tetraits.LOGGER.error("Couldn't load file {}.", file, e);
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        Tetraits.LOGGER.error("Failed to visit file.", exc);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Nullable
    public MinecraftServer server;

    protected ThreadTaskExecutor<? extends Runnable> getExecutor() {
        return server == null ? Minecraft.getInstance() : server;
    }

}
