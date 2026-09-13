package com.automation.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Small file helpers for download/upload tests.
 */
public final class FileHelper {

    private FileHelper() {
    }

    public static Path downloadDir() {
        Path dir = Paths.get(System.getProperty("user.dir"), "test-output", "downloads").toAbsolutePath();
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new IllegalStateException("Could not create download dir: " + dir, e);
        }
        return dir;
    }

    public static void cleanDirectory(Path dir) {
        if (!Files.exists(dir)) {
            return;
        }
        try (Stream<Path> files = Files.list(dir)) {
            files.forEach(path -> {
                try {
                    if (Files.isDirectory(path)) {
                        try (Stream<Path> walk = Files.walk(path)) {
                            walk.sorted(Comparator.reverseOrder()).forEach(p -> p.toFile().delete());
                        }
                    } else {
                        Files.deleteIfExists(path);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException("Could not clean " + path, e);
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("Could not list " + dir, e);
        }
    }

    public static Path waitForFile(Path dir, String fileName, int timeoutSeconds) {
        long deadline = System.currentTimeMillis() + timeoutSeconds * 1000L;
        Path target = dir.resolve(fileName);
        while (System.currentTimeMillis() < deadline) {
            if (Files.exists(target) && target.toFile().length() > 0) {
                return target;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while waiting for " + fileName);
            }
        }
        throw new AssertionError("File was not downloaded in time: " + target);
    }
}
