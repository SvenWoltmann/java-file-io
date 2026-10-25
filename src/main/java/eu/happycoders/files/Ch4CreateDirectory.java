package eu.happycoders.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Ch4CreateDirectory {

  static void main() throws IOException {
    Examples.reset();
    Path logDir = Path.of("target/logs");

    System.out.println("== createDirectory()");
    Path archive = logDir.resolve("archive");
    Files.createDirectory(archive);
    System.out.println("isDirectory(archive): " + Files.isDirectory(archive));

    System.out.println("== createDirectory() twice");
    try {
      Files.createDirectory(archive);
    } catch (FileAlreadyExistsException e) {
      System.out.println(e);
    }

    System.out.println("== createDirectory() without parent");
    try {
      Files.createDirectory(logDir.resolve("archive/2026/10"));
    } catch (NoSuchFileException e) {
      System.out.println(e);
    }

    System.out.println("== createDirectories()");
    Path month = logDir.resolve("archive/2026/10");
    Files.createDirectories(month);
    Files.createDirectories(month);
    Examples.printTree(logDir);

    System.out.println("== createDirectories() where a file is");
    try {
      Files.createDirectories(logDir.resolve("server.log"));
    } catch (FileAlreadyExistsException e) {
      System.out.println(e);
    }

    System.out.println("== File.mkdir() and mkdirs()");
    System.out.println("mkdir():  " + new File("target/logs/archive").mkdir());
    System.out.println("mkdirs(): " + new File("target/logs/server.log/x").mkdirs());
  }
}
