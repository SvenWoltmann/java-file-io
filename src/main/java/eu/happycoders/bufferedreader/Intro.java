package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Intro {

  static void main() throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Path.of("files/server.log"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    }
  }
}
