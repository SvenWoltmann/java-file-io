package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Ch4ReadAll {

  static void main() throws IOException {
    Path log = Path.of("files/server.log");

    System.out.println("== readAllLines()");
    try (BufferedReader reader = Files.newBufferedReader(log)) {
      List<String> lines = reader.readAllLines();
      System.out.println(lines.size() + " lines");
    }

    System.out.println("== readAllAsString()");
    try (BufferedReader reader = Files.newBufferedReader(log)) {
      String content = reader.readAllAsString();
      System.out.println(content.length() + " characters");
    }

    System.out.println("== readAllLines() after readLine()");
    try (BufferedReader reader = Files.newBufferedReader(log)) {
      String first = reader.readLine();
      List<String> rest = reader.readAllLines();
      System.out.println("First line: " + first);
      System.out.println("Remaining:  " + rest.size() + " lines");
    }
  }
}
