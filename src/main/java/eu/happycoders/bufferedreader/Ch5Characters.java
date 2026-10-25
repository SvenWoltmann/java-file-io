package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch5Characters {

  static void main() throws IOException {
    Path log = Path.of("files/server.log");

    System.out.println("== read()");
    try (BufferedReader reader = Files.newBufferedReader(log)) {
      int digits = 0;
      int c;
      while ((c = reader.read()) != -1) {
        if (Character.isDigit(c)) {
          digits++;
        }
      }
      System.out.println(digits + " digits");
    }

    System.out.println("== read(char[], int, int)");
    try (BufferedReader reader = Files.newBufferedReader(log)) {
      char[] buffer = new char[100];
      int calls = 0;
      int total = 0;
      int n;
      while ((n = reader.read(buffer, 0, buffer.length)) != -1) {
        calls++;
        total += n;
      }
      System.out.println(total + " characters in " + calls + " calls");
    }
  }
}
