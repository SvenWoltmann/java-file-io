package eu.happycoders.listing;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;

/** The directory tree the examples list, plus helpers for the trees some examples build. */
public final class Examples {

  /** The directory the examples list: {@code files/project/} with five files in four folders. */
  public static final Path PROJECT = Path.of("files/project");

  private Examples() {}

  /**
   * Creates {@code target/restricted/} with a readable directory {@code public/} and a directory
   * {@code private/} that the current user may not read, and returns the root.
   */
  public static Path createRestrictedTree() throws IOException {
    Path root = Path.of("target/restricted");
    if (Files.exists(root)) {
      unlock(root.resolve("private"));
      deleteDirectory(root);
    }
    Files.createDirectories(root.resolve("public"));
    Files.createDirectories(root.resolve("private"));
    Files.writeString(root.resolve("public/notes.txt"), "public notes\n");
    Files.writeString(root.resolve("private/secret.txt"), "top secret\n");
    Files.setPosixFilePermissions(
        root.resolve("private"), PosixFilePermissions.fromString("---------"));
    return root;
  }

  /** Makes {@code dir} readable again, so it can be deleted. */
  public static void unlock(Path dir) throws IOException {
    if (Files.exists(dir)) {
      Files.setPosixFilePermissions(dir, PosixFilePermissions.fromString("rwx------"));
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
