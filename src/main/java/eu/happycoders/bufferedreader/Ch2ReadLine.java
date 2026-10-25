package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Ch2ReadLine {

  static void main() throws IOException {
    System.out.println("== Line terminators");
    printLines("Unix\nWindows\r\nClassic Mac OS\rLast line without terminator");

    System.out.println("== Empty lines");
    printLines("first\n\nthird\n");

    System.out.println("== Two line feeds at the end");
    printLines("first\n\n");

    System.out.println("== Empty input");
    printLines("");
  }

  private static void printLines(String text) throws IOException {
    try (BufferedReader reader = new BufferedReader(Reader.of(text))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println("[" + line + "]");
      }
      System.out.println("null");
    }
  }
}
