package eu.happycoders.filereader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Ch2ReadAll {

  static void main() throws IOException {
    System.out.println("== readAllLines()");
    try (FileReader reader = new FileReader("files/server.log")) {
      List<String> lines = reader.readAllLines();
      System.out.println(lines.size() + " lines, the last one: " + lines.getLast());
    }

    System.out.println("== readAllAsString() after read()");
    try (FileReader reader = new FileReader("files/server.log")) {
      reader.skip(20);
      String rest = reader.readAllAsString();
      System.out.println(rest.lines().findFirst().orElseThrow());
    }
  }
}
