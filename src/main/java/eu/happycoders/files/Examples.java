package eu.happycoders.files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/** The starting point of every example: a fresh {@code target/logs/} with one log file in it. */
public final class Examples {

  /** The directory the examples work in. */
  public static final Path LOG_DIR = Path.of("target/logs");

  /** The log file the examples start with, a copy of {@code files/server.log}. */
  public static final Path LOG = LOG_DIR.resolve("server.log");

  private Examples() {}

  /** Deletes {@code target/logs/} and recreates it with a copy of {@code files/server.log}. */
  public static void reset() throws IOException {
    if (Files.exists(LOG_DIR)) {
      deleteDirectory(LOG_DIR);
    }
    Files.createDirectories(LOG_DIR);
    Files.copy(Path.of("files/server.log"), LOG, REPLACE_EXISTING);
  }

  /** Prints every entry below {@code dir}, directories with a slash, files with their size. */
  public static void printTree(Path dir) throws IOException {
    try (Stream<Path> paths = Files.walk(dir)) {
      paths
          .filter(path -> !path.equals(dir))
          .sorted()
          .forEach(
              path -> {
                String name = dir.relativize(path).toString();
                try {
                  if (Files.isDirectory(path)) {
                    System.out.println(name + "/");
                  } else {
                    System.out.println(name + " (" + Files.size(path) + " bytes)");
                  }
                } catch (IOException e) {
                  System.out.println(name + " (" + e + ")");
                }
              });
    }
  }

  /** Deletes {@code dir} with all its contents, files first, then the empty directories. */
  public static void deleteDirectory(Path dir) throws IOException {
    Files.walkFileTree(
        dir,
        new SimpleFileVisitor<>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            if (exc != null) {
              throw exc;
            }
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
          }
        });
  }
}
