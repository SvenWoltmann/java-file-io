package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Ch6MarkReset {

  static void main() throws IOException {
    System.out.println("== With header");
    printData(Reader.of("# exported 2026-10-17\nAda Lovelace\nAlan Turing\n"));

    System.out.println("== Without header");
    printData(Reader.of("Ada Lovelace\nAlan Turing\n"));

    System.out.println("== skip()");
    try (BufferedReader reader =
        new BufferedReader(Reader.of("2026-10-17 08:15:02 INFO  Started"))) {
      long skipped = reader.skip(20);
      System.out.println("Skipped " + skipped + " characters: " + reader.readLine());
    }
  }

  private static void printData(Reader source) throws IOException {
    try (BufferedReader reader = new BufferedReader(source)) {
      reader.mark(1_000);
      String first = reader.readLine();
      if (first != null && first.startsWith("#")) {
        System.out.println("Header: " + first);
      } else {
        reader.reset();
      }

      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println("Data:   " + line);
      }
    }
  }
}
