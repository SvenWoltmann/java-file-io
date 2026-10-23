package eu.happycoders.printwriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch4Flush {

  static void main() throws IOException {
    Path file = Path.of("target/flush.log");

    System.out.println("== Without autoFlush");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      out.println("2026-10-17 08:15:02 INFO  Server started on port 8080");
      System.out.println("After println(): " + Files.size(file) + " bytes");
      out.flush();
      System.out.println("After flush():   " + Files.size(file) + " bytes");
    }

    System.out.println("== With autoFlush");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file), true)) {
      out.print("2026-10-17 08:15:07 ");
      System.out.println("After print():      " + Files.size(file) + " bytes");
      out.println("INFO  GET /java/java-bufferedreader/ 200");
      System.out.println("After println():    " + Files.size(file) + " bytes");
      out.print("2026-10-17 08:15:09 WARN  GET /java/bufferedreader/ 404\n");
      System.out.println("After print(...\\n): " + Files.size(file) + " bytes");
      out.printf("%s %-5s %s%n", "2026-10-17 08:15:11", "INFO", "GET /java/java-streams/ 200");
      System.out.println("After printf():     " + Files.size(file) + " bytes");
    }
  }
}
