package eu.happycoders.files;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public class Ch7CopyDirectory {

  static void main() throws IOException {
    Examples.reset();
    Path logDir = Path.of("target/logs");
    Path month = Files.createDirectories(logDir.resolve("archive/2026-10"));
    Files.copy(Examples.LOG, month.resolve("server.log.1"));
    Files.copy(Examples.LOG, month.resolve("server.log.2"));

    System.out.println("== copy() on a directory");
    Path emptyCopy = Files.copy(logDir, Path.of("target/logs-copy"));
    try (Stream<Path> entries = Files.list(emptyCopy)) {
      System.out.println(emptyCopy + ": " + entries.count() + " entries");
    }
    Files.delete(emptyCopy);

    System.out.println("== copyDirectory()");
    Path backup = Path.of("target/backup");
    if (Files.exists(backup)) {
      Examples.deleteDirectory(backup);
    }
    copyDirectory(logDir, backup);
    Examples.printTree(backup);

    System.out.println("== copyDirectory() a second time");
    try {
      copyDirectory(logDir, backup);
    } catch (FileAlreadyExistsException e) {
      System.out.println(e);
    }
  }

  private static void copyDirectory(Path sourceDir, Path targetDir) throws IOException {
    Files.walkFileTree(
        sourceDir,
        new SimpleFileVisitor<>() {
          @Override
          public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
              throws IOException {
            Files.createDirectories(targetDir.resolve(sourceDir.relativize(dir)));
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException {
            Files.copy(file, targetDir.resolve(sourceDir.relativize(file)), NOFOLLOW_LINKS);
            return FileVisitResult.CONTINUE;
          }
        });
  }
}
