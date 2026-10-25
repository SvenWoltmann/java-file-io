package eu.happycoders.files;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

public class Ch2Exists {

  static void main() throws IOException {
    Examples.reset();

    System.out.println("== exists(), isRegularFile(), isDirectory(), notExists()");
    Path logDir = Path.of("target/logs");
    Path log = logDir.resolve("server.log");
    Path missing = logDir.resolve("missing.log");

    System.out.println("exists(log):          " + Files.exists(log));
    System.out.println("isRegularFile(log):   " + Files.isRegularFile(log));
    System.out.println("isDirectory(log):     " + Files.isDirectory(log));
    System.out.println("isDirectory(logDir):  " + Files.isDirectory(logDir));
    System.out.println("exists(missing):      " + Files.exists(missing));
    System.out.println("notExists(missing):   " + Files.notExists(missing));

    System.out.println("== a symbolic link whose target is gone");
    Path link = logDir.resolve("current.log");
    Files.createSymbolicLink(link, Path.of("server.log"));
    System.out.println("exists(link):                 " + Files.exists(link));
    Files.delete(log);
    System.out.println("exists(link) after delete:    " + Files.exists(link));
    System.out.println("exists(link, NOFOLLOW_LINKS): " + Files.exists(link, NOFOLLOW_LINKS));
    System.out.println("isSymbolicLink(link):         " + Files.isSymbolicLink(link));

    System.out.println("== existence cannot be determined");
    Path locked = logDir.resolve("locked");
    Path secret = locked.resolve("secret.log");
    Files.createDirectory(locked);
    Files.createFile(secret);
    Files.setPosixFilePermissions(locked, PosixFilePermissions.fromString("---------"));
    try {
      System.out.println("exists(secret):    " + Files.exists(secret));
      System.out.println("notExists(secret): " + Files.notExists(secret));
    } finally {
      Files.setPosixFilePermissions(locked, PosixFilePermissions.fromString("rwx------"));
    }
  }
}
