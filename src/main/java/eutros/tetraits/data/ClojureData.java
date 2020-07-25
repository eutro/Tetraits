package eutros.tetraits.data;

import clojure.java.api.Clojure;
import clojure.lang.Compiler.CompilerException;
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
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public abstract class ClojureData implements FileVisitor<Path> {

    public final Map<ResourceLocation, IFn> data = new HashMap<>();
    public static final IFn loadClass = Clojure.var("clojure.core", "load-file");

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
            DataManager.getInstance().LOGGER.warn("File: \"{}\" should be in a name-spaced subfolder!", file.normalize());
            return FileVisitResult.CONTINUE;
        }

        ResourceLocation rl = new ResourceLocation(relative.getName(0).toString(),
                FilenameUtils.removeExtension(
                        relative.subpath(1, relative.getNameCount())
                                .toString()));

        getExecutor().runImmediately(() -> {
            try {
                try {
                    Object loaded = loadClass.invoke(file.normalize().toString());
                    if(loaded instanceof IFn) {
                        data.put(rl, (IFn) loaded);
                        DataManager.getInstance().LOGGER.debug("Loaded {}.", rl);
                    } else if(loaded != null) {
                        DataManager.getInstance().LOGGER.error("{} didn't return IFn.", rl);
                    }
                } catch(CompilerException compilerError) {
                    IPersistentMap data = compilerError.getData();
                    Integer column = (Integer) data.valAt(CompilerException.ERR_COLUMN);
                    StringBuilder error = new StringBuilder("Couldn't compile \"{}\".\n");
                    error.append("   Error at line: {}\n");
                    error.append("          column: {}\n");

                    try {
                        BufferedReader br = Files.newBufferedReader(file);
                        br.lines()
                                .skip(compilerError.line - 1)
                                .findFirst()
                                .ifPresent(line -> {
                                    error.append("=====================================================\n");
                                    error.append(line);
                                    error.append('\n');
                                    error.append(new String(new char[column]).replace('\0', ' '));
                                    error.append("^ here\n");
                                    error.append("=====================================================\n");
                                });
                        br.close();
                    } catch(IOException e) {
                        // oh what the hell
                    }

                    error.append("    {}\n");

                    Throwable cause = compilerError.getCause();
                    while(cause != null) {
                        error.append(String.format("    Caused by: %s\n", cause));
                        cause = cause.getCause();
                    }

                    DataManager.getInstance().LOGGER.error(error.toString(),
                            rl,
                            compilerError.line,
                            column,
                            compilerError.toString());
                }
            } catch(RuntimeException e) {
                // Clojure rethrows IOExceptions as such
                DataManager.getInstance().LOGGER.error("Couldn't load file {}.", file, e);
            }
        });

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        DataManager.getInstance().LOGGER.error("Failed to visit file.", exc);
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
