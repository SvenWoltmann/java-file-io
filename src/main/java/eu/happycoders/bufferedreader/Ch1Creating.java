package eu.happycoders.bufferedreader;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Ch1Creating {

  static void main() throws IOException {
    Path log = Path.of("files/server.log");

    System.out.println("== Files.newBufferedReader()");
    try (BufferedReader reader = Files.newBufferedReader(log)) {
      System.out.println(reader.readLine());
    }

    System.out.println("== FileReader");
    try (BufferedReader reader = new BufferedReader(new FileReader("files/server.log", UTF_8))) {
      System.out.println(reader.readLine());
    }

    System.out.println("== InputStreamReader around a GZIPInputStream");
    Path gzipped = Path.of("target/server.log.gz");
    try (OutputStream out = new GZIPOutputStream(Files.newOutputStream(gzipped))) {
      Files.copy(log, out);
    }
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(new GZIPInputStream(Files.newInputStream(gzipped)), UTF_8))) {
      System.out.println(reader.readLine());
    }

    System.out.println("== Own buffer size");
    try (BufferedReader reader = new BufferedReader(new FileReader(log.toFile(), UTF_8), 65_536)) {
      System.out.println(reader.readLine());
    }

    System.out.println("== A Latin-1 file read as UTF-8");
    Path latin1 = Path.of("target/greeting-latin1.txt");
    Files.writeString(latin1, "Grüße aus Köln", ISO_8859_1);

    try (BufferedReader reader = Files.newBufferedReader(latin1)) {
      System.out.println(reader.readLine());
    } catch (MalformedInputException e) {
      System.out.println("Files.newBufferedReader(): " + e);
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(latin1.toFile(), UTF_8))) {
      System.out.println("FileReader:                " + reader.readLine());
    }

    try (BufferedReader reader = Files.newBufferedReader(latin1, ISO_8859_1)) {
      System.out.println("With ISO_8859_1:           " + reader.readLine());
    }
  }
}
