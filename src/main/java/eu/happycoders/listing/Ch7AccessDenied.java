package eu.happycoders.listing;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public class Ch7AccessDenied {

  static void main() throws IOException {
    Path restricted = Examples.createRestrictedTree();
    try {
      System.out.println("== walk()");
      try (Stream<Path> paths = Files.walk(restricted)) {
        paths.forEach(System.out::println);
      } catch (UncheckedIOException e) {
        System.out.println(e);
      }

      System.out.println("== find()");
      try (Stream<Path> matches =
          Files.find(restricted, Integer.MAX_VALUE, (path, attrs) -> attrs.isRegularFile())) {
        matches.forEach(System.out::println);
      } catch (UncheckedIOException e) {
        System.out.println(e);
      }

      System.out.println("== walkFileTree() with SimpleFileVisitor");
      try {
        Files.walkFileTree(
            restricted,
            new SimpleFileVisitor<>() {
              @Override
              public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                System.out.println(file);
                return FileVisitResult.CONTINUE;
              }
            });
      } catch (AccessDeniedException e) {
        System.out.println(e);
      }

      System.out.println("== walkFileTree() with visitFileFailed()");
      Files.walkFileTree(
          restricted,
          new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
              System.out.println(file);
              return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
              if (exc instanceof AccessDeniedException) {
                System.out.println("Access denied: " + file);
                return FileVisitResult.CONTINUE;
              }
              throw exc;
            }
          });

      System.out.println("== list() with own recursion");
      listRecursively(restricted);
    } finally {
      Examples.unlock(restricted.resolve("private"));
    }
  }

  private static void listRecursively(Path dir) throws IOException {
    try (Stream<Path> entries = Files.list(dir)) {
      entries.forEach(
          entry -> {
            System.out.println(entry);
            if (Files.isDirectory(entry)) {
              try {
                listRecursively(entry);
              } catch (AccessDeniedException e) {
                System.out.println("Access denied: " + entry);
              } catch (IOException e) {
                throw new UncheckedIOException(e);
              }
            }
          });
    }
  }
}
