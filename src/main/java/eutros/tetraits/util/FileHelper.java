package eutros.tetraits.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileHelper {

    public static void copyAll(Path from, Path to, CopyOption... options) {
        try {
            Files.walkFileTree(from, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    Path newDir = to.resolve(from.relativize(dir));
                    try {
                        Files.copy(dir, newDir, options);
                    } catch(FileAlreadyExistsException ignored) {
                    } catch (IOException e) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    try {
                        Files.copy(file, to.resolve(from.relativize(file)), options);
                    } catch(IOException ignored) {
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException ignored) {
        }
    }

}
