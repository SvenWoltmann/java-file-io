package eu.happycoders.printwriter;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Ch1Creating {

  static void main() throws IOException {
    System.out.println("== new PrintWriter(String)");
    try (PrintWriter out = new PrintWriter("target/greeting.txt")) {
      out.println("Grüße aus Köln");
    }
    Path greeting = Path.of("target/greeting.txt");
    System.out.println(Files.readString(greeting).strip());
    System.out.println(Files.size(greeting) + " bytes");

    System.out.println("== new PrintWriter(String, Charset)");
    try (PrintWriter out = new PrintWriter("target/greeting-latin1.txt", ISO_8859_1)) {
      out.println("Grüße aus Köln");
    }
    Path latin1 = Path.of("target/greeting-latin1.txt");
    System.out.println(Files.readString(latin1, ISO_8859_1).strip());
    System.out.println(Files.size(latin1) + " bytes");

    System.out.println("== Files.newBufferedWriter()");
    Path log = Path.of("target/server.log");
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(log))) {
      out.println("2026-10-17 08:15:02 INFO  Server started on port 8080");
    }
    System.out.print(Files.readString(log));

    System.out.println("== PrintWriter around a GZIPOutputStream");
    Path gzipped = Path.of("target/server.log.gz");
    try (PrintWriter out =
        new PrintWriter(new GZIPOutputStream(Files.newOutputStream(gzipped)), false, UTF_8)) {
      for (LogEntry entry : LogEntry.SERVER_LOG) {
        out.printf("%s %-5s %s%n", entry.timestamp(), entry.level(), entry.message());
      }
    }
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(new GZIPInputStream(Files.newInputStream(gzipped)), UTF_8))) {
      System.out.println(reader.readLine());
      System.out.println(reader.lines().count() + 1 + " lines");
    }
  }
}
