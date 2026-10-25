package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class Ch8Scanner {

  static void main() {
    String line = "2026-10-17 08:15:02 INFO  Server started on port 8080";

    System.out.println("== Tokens with Scanner");
    try (Scanner scanner = new Scanner(line)) {
      String date = scanner.next();
      String time = scanner.next();
      String level = scanner.next();
      String message = scanner.nextLine().strip();
      System.out.println(date + " | " + time + " | " + level + " | " + message);
    }

    System.out.println("== Tokens with split()");
    String[] parts = line.split("\\s+", 4);
    System.out.println(String.join(" | ", parts));

    System.out.println("== An IOException with BufferedReader");
    try (BufferedReader reader = new BufferedReader(new FailingReader())) {
      System.out.println(reader.readLine());
    } catch (IOException e) {
      System.out.println("Caught: " + e);
    }

    System.out.println("== The same IOException with Scanner");
    try (Scanner scanner = new Scanner(new FailingReader())) {
      System.out.println("hasNextLine(): " + scanner.hasNextLine());
      System.out.println("ioException(): " + scanner.ioException());
    }
  }

  /** A reader whose source fails on the first read – like a network drive that disappears. */
  private static class FailingReader extends Reader {
    @Override
    public int read(char[] buffer, int offset, int length) throws IOException {
      throw new IOException("Simulated read error");
    }

    @Override
    public void close() {
      // nothing to close
    }
  }
}
