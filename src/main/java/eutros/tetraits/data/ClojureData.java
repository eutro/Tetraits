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
import java.io.BufferedReader;
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

    public abstract String getPath();

    public void load() {
        data.clear();

        if(Files.exists(getDataRoot())) {
            try {
                Files.walkFileTree(getDataRoot(), this);
            } catch(IOException ignored) {
                // This shouldn't happen.
            }
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
                        Tetraits.LOGGER.error("{} didn't return IFn.", rl);
                    }
                } catch(Compiler.CompilerException compilerError) {
                    BufferedReader br = null;
                    try {
                        br = Files.newBufferedReader(file);
                    } catch(IOException e) {
                        // oh what the hell
                    }
                    IPersistentMap data = compilerError.getData();
                    Integer column = (Integer) data.valAt(Compiler.CompilerException.ERR_COLUMN);
                    StringBuilder error = new StringBuilder("Couldn't compile \"{}\".\n");
                    error.append("   Error at line: {}\n");
                    error.append("          column: {}\n");

                    if(br != null) {
                        br.lines()
                                .skip(compilerError.line - 1)
                                .findFirst()
                                .ifPresent(line -> {
                                    error.append("==========================================================\n");
                                    error.append(line);
                                    error.append('\n');
                                    error.append(new String(new char[column]).replace('\0', ' '));
                                    error.append("^ here\n");
                                    error.append("==========================================================\n");
                                });
                    }

                    error.append("    {}\n");

                    Throwable cause = compilerError.getCause();
                    while(cause != null) {
                        error.append(String.format("    Caused by: %s\n", cause));
                        cause = cause.getCause();
                    }

                    Tetraits.LOGGER.error(error.toString(),
                            rl,
                            compilerError.line,
                            column,
                            compilerError.toString());
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
