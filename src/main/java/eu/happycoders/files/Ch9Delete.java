package eu.happycoders.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Ch9Delete {

  static void main() throws IOException {
    Examples.reset();
    Path logDir = Path.of("target/logs");
    Path log = logDir.resolve("server.log");

    System.out.println("== delete()");
    Files.delete(log);
    System.out.println("exists(log): " + Files.exists(log));

    System.out.println("== delete() twice");
    try {
      Files.delete(log);
    } catch (NoSuchFileException e) {
      System.out.println(e);
    }

    System.out.println("== deleteIfExists()");
    System.out.println("deleteIfExists(log): " + Files.deleteIfExists(log));

    System.out.println("== delete() on a directory with contents");
    Path archive = Files.createDirectory(logDir.resolve("archive"));
    Files.copy(Path.of("files/server.log"), archive.resolve("server.log.1"));
    try {
      Files.delete(archive);
    } catch (DirectoryNotEmptyException e) {
      System.out.println(e);
    }

    System.out.println("== delete() on a symbolic link");
    Path link = logDir.resolve("current.log");
    Files.createSymbolicLink(link, Path.of("archive/server.log.1"));
    Files.delete(link);
    Examples.printTree(logDir);

    System.out.println("== File.delete()");
    System.out.println("File.delete(): " + new File("target/logs/archive").delete());
  }
}
