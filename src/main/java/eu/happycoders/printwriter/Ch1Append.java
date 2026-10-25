package eu.happycoders.printwriter;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Ch1Append {

  static void main() throws IOException {
    Path log = Path.of("target/append.log");
    Files.deleteIfExists(log);

    System.out.println("== APPEND only");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(log, APPEND))) {
      out.println("first run");
    } catch (NoSuchFileException e) {
      System.out.println(e);
    }

    System.out.println("== CREATE and APPEND, twice");
    for (String run : new String[] {"first run", "second run"}) {
      try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(log, CREATE, APPEND))) {
        out.println(run);
      }
    }
    System.out.print(Files.readString(log));

    System.out.println("== FileWriter with append = true");
    try (PrintWriter out =
        new PrintWriter(new BufferedWriter(new FileWriter(log.toFile(), UTF_8, true)))) {
      out.println("third run");
    }
    System.out.print(Files.readString(log));
  }
}
