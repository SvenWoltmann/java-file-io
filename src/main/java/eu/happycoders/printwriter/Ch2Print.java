package eu.happycoders.printwriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Ch2Print {

  static void main() throws IOException {
    Path file = Path.of("target/print.txt");

    System.out.println("== print() and println()");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      out.print(404);
      out.print(' ');
      out.print(3.5);
      out.print(' ');
      out.print(true);
      out.print(' ');
      out.println(List.of("GET", "POST"));
      String missing = null;
      out.println(missing);
      out.println();
      out.println("end");
    }
    System.out.print(Files.readString(file));

    System.out.println("== The line separator");
    String text = Files.readString(file);
    System.out.println(text.replace("\r", "\\r").replace("\n", "\\n"));

    System.out.println("== write(int) vs. print(int)");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      out.write(65);
      out.println();
      out.print(65);
      out.println();
    }
    System.out.print(Files.readString(file));

    System.out.println("== append()");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      out.append("GET ").append("/java/java-streams/").append(' ').append("200").println();
    }
    System.out.print(Files.readString(file));

    System.out.println("== print(char[]) with null");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      char[] chars = null;
      out.print(chars);
    } catch (NullPointerException e) {
      System.out.println(e);
    }
  }
}
