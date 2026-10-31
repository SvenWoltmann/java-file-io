package eu.happycoders.filereader;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ch5BufferedReader {

  static void main() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("files/server.log", UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    }
  }
}
