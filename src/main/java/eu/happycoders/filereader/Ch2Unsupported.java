package eu.happycoders.filereader;

import java.io.FileReader;
import java.io.IOException;

public class Ch2Unsupported {

  static void main() throws IOException {
    try (FileReader reader = new FileReader("files/server.log")) {
      System.out.println("markSupported(): " + reader.markSupported());
      try {
        reader.mark(100);
      } catch (IOException e) {
        System.out.println(e);
      }
      try {
        reader.reset();
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}
