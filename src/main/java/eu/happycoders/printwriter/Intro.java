package eu.happycoders.printwriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Intro {

  static void main() throws IOException {
    Path log = Path.of("target/server.log");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(log))) {
      for (LogEntry entry : LogEntry.SERVER_LOG) {
        out.printf("%s %-5s %s%n", entry.timestamp(), entry.level(), entry.message());
      }
    }

    System.out.print(Files.readString(log));
  }
}
