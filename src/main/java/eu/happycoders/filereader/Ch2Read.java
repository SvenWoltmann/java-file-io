package eu.happycoders.filereader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch2Read {

  static void main() throws IOException {
    System.out.println("== read()");
    try (FileReader reader = new FileReader("files/server.log")) {
      int c;
      int count = 0;
      while ((c = reader.read()) != -1) {
        if (c == '\n') {
          count++;
        }
      }
      System.out.println(count + " lines");
    }

    System.out.println("== read() and surrogate pairs");
    Path coffee = Path.of("target/coffee.txt");
    Files.writeString(coffee, "☕🚀");
    try (FileReader reader = new FileReader(coffee.toFile())) {
      int c;
      while ((c = reader.read()) != -1) {
        System.out.println(Integer.toHexString(c) + " " + Character.getName(c));
      }
    }
  }
}
