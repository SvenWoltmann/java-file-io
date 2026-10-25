package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch10Mistakes {

  static void main() throws IOException {
    System.out.println("== readLine() twice per iteration");
    try (BufferedReader reader = Files.newBufferedReader(Path.of("files/server.log"))) {
      while (reader.readLine() != null) {
        System.out.println(reader.readLine());
      }
    }
  }
}
