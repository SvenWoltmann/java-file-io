package eu.happycoders.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.stream.Stream;

public class Ch10DeleteDirectory {

  static void main() throws IOException {
    Examples.reset();
    Path logDir = Path.of("target/logs");

    System.out.println("== deleteDirectory()");
    Path month = Files.createDirectories(logDir.resolve("archive/2026-10"));
    Files.copy(Examples.LOG, month.resolve("server.log.1"));
    Files.createSymbolicLink(month.resolve("latest.log"), Path.of("../../server.log"));
    deleteDirectory(logDir.resolve("archive"));
    Examples.printTree(logDir);

    System.out.println("== deleteDirectoryWithWalk()");
    Files.createDirectories(month);
    Files.copy(Examples.LOG, month.resolve("server.log.1"));
    deleteDirectoryWithWalk(logDir.resolve("archive"));
    Examples.printTree(logDir);

    System.out.println("== deleteDirectory() on a missing directory");
    try {
      deleteDirectory(logDir.resolve("archive"));
    } catch (NoSuchFileException e) {
      System.out.println(e);
    }
  }

  private static void deleteDirectory(Path dirToDelete) throws IOException {
    Files.walkFileTree(
        dirToDelete,
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

  private static void deleteDirectoryWithWalk(Path dirToDelete) throws IOException {
    try (Stream<Path> paths = Files.walk(dirToDelete)) {
      paths
          .sorted(Comparator.reverseOrder())
          .forEach(
              path -> {
                try {
                  Files.delete(path);
                } catch (IOException e) {
                  throw new UncheckedIOException(e);
                }
              });
    }
  }
}
