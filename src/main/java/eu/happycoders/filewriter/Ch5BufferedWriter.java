package eu.happycoders.filewriter;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch5BufferedWriter {

  static void main() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("target/server.log", UTF_8))) {
      writer.write("2026-10-17 08:15:02 INFO  Server started on port 8080");
      writer.newLine();
      writer.write("2026-10-17 08:15:12 ERROR Connection reset by peer");
      writer.newLine();
    }
    System.out.print(Files.readString(Path.of("target/server.log")));
  }
}
