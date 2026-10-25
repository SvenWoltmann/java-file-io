package eu.happycoders.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Ch1FilesVsFile {

  static void main() throws IOException {
    Examples.reset();

    System.out.println("== renameTo() vs. move()");
    File log = new File("target/logs/server.log");
    File target = new File("target/old/server.log");
    System.out.println("renameTo(): " + log.renameTo(target));
    try {
      Files.move(log.toPath(), target.toPath());
    } catch (IOException e) {
      System.out.println(e);
    }

    System.out.println("== delete() vs. delete()");
    File missing = new File("target/logs/missing.log");
    System.out.println("File.delete(): " + missing.delete());
    try {
      Files.delete(missing.toPath());
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
