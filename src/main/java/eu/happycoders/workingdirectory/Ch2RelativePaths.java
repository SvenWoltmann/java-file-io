package eu.happycoders.workingdirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/** Start it in the repository root and in target/ to see the difference. */
public class Ch2RelativePaths {

  static void main() throws IOException {
    Path log = Path.of("files/server.log");
    try {
      String content = Files.readString(log);
      System.out.println("Read " + content.lines().count() + " lines");
    } catch (NoSuchFileException e) {
      System.out.println(e);
      System.out.println("Absolute path:");
      System.out.println("  " + log.toAbsolutePath());
    }
  }
}
