package eu.happycoders.filereader;

import java.io.FileReader;
import java.io.IOException;

public class Intro {

  static void main() throws IOException {
    try (FileReader reader = new FileReader("files/server.log")) {
      String text = reader.readAllAsString();
      System.out.print(text);
    }
  }
}
