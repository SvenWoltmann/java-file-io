package eu.happycoders.filewriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Intro {

  static void main() throws IOException {
    try (FileWriter writer = new FileWriter("target/server.log")) {
      writer.write("2026-10-17 08:15:02 INFO  Server started on port 8080\n");
      writer.write("2026-10-17 08:15:12 ERROR Connection reset by peer\n");
    }

    System.out.print(Files.readString(Path.of("target/server.log")));
  }
}
