package eu.happycoders.listing;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Ch4WalkFileTree {

  static void main() throws IOException {
    Path project = Path.of("files/project");

    System.out.println("== walkFileTree(project): .java files");
    Files.walkFileTree(
        project,
        new SimpleFileVisitor<>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (attrs.isRegularFile() && file.toString().endsWith(".java")) {
              System.out.println(file);
            }
            return FileVisitResult.CONTINUE;
          }
        });

    System.out.println("== walkFileTree(project): call order");
    Files.walkFileTree(project, new LoggingVisitor());

    System.out.println("== walkFileTree(project): SKIP_SUBTREE for src");
    Files.walkFileTree(
        project,
        new LoggingVisitor() {
          @Override
          public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            super.preVisitDirectory(dir, attrs);
            return dir.endsWith("src") ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
          }
        });

    System.out.println("== walkFileTree(project): TERMINATE at the first .java file");
    Files.walkFileTree(
        project,
        new LoggingVisitor() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            super.visitFile(file, attrs);
            return file.toString().endsWith(".java")
                ? FileVisitResult.TERMINATE
                : FileVisitResult.CONTINUE;
          }
        });
  }

  /** Prints every call {@code walkFileTree()} makes, numbered, and continues. */
  static class LoggingVisitor extends SimpleFileVisitor<Path> {
    private int step;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
      System.out.println(++step + ". preVisitDirectory(" + dir + ")");
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
      System.out.println(++step + ". visitFile(" + file + ")");
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
      System.out.println(++step + ". postVisitDirectory(" + dir + ")");
      return FileVisitResult.CONTINUE;
    }
  }
}
