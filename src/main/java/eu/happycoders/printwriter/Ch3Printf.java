package eu.happycoders.printwriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class Ch3Printf {

  static void main() throws IOException {
    Path file = Path.of("target/printf.txt");

    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      out.printf("%s %-5s %s%n", "2026-10-17 08:15:12", "ERROR", "Connection reset by peer");
      out.printf("[%-5s] [%5s]%n", "WARN", "WARN");
      out.printf("%d of %d requests failed (%d %%)%n", 1, 4, 25);
      out.printf("Request #%05d%n", 42);
    }
    System.out.print(Files.readString(file));

    System.out.println("== Locale");
    System.out.println("Default locale: " + Locale.getDefault());
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      out.printf("%.2f ms%n", 1234.5);
      out.printf(Locale.GERMANY, "%,.2f ms%n", 1234.5);
      out.printf(Locale.US, "%,.2f ms%n", 1234.5);
      out.printf(Locale.ROOT, "%.2f ms%n", 1234.5);
      out.print(1234.5);
      out.println(" ms");
    }
    System.out.print(Files.readString(file));

    System.out.println("== printf() returns the PrintWriter");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      out.printf("%d ", 1).printf("%d ", 2).format("%d%n", 3);
    }
    System.out.print(Files.readString(file));

    System.out.println("== A missing argument");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      out.printf("%s %-5s %s%n", "2026-10-17 08:15:12", "ERROR");
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }
    System.out.println("In the file: \"" + Files.readString(file) + "\"");
  }
}
