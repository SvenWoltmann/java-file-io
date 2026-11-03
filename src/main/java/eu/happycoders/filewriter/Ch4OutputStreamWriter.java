package eu.happycoders.filewriter;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Ch4OutputStreamWriter {

  static void main() throws IOException {
    System.out.println("== OutputStreamWriter around a GZIPOutputStream");
    Path gzip = Path.of("target/server.log.gz");
    OutputStream out = new GZIPOutputStream(Files.newOutputStream(gzip));
    try (OutputStreamWriter writer = new OutputStreamWriter(out, UTF_8)) {
      for (int i = 0; i < 1000; i++) {
        writer.write("2026-10-17 08:15:07 INFO  GET /java/java-filewriter/ 200\n");
      }
    }
    System.out.println("Compressed: " + Files.size(gzip) + " bytes");
    try (InputStream in = new GZIPInputStream(Files.newInputStream(gzip))) {
      System.out.println("Unpacked:   " + in.readAllBytes().length + " bytes");
    }

    System.out.println("== Unknown charset name");
    try {
      new OutputStreamWriter(OutputStream.nullOutputStream(), "UTF-9");
    } catch (UnsupportedEncodingException e) {
      System.out.println(e);
    }
  }
}
