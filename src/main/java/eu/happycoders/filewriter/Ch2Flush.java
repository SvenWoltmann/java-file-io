package eu.happycoders.filewriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch2Flush {

  static void main() throws IOException {
    System.out.println("== write() and flush()");
    Path log = Path.of("target/server.log");
    try (FileWriter writer = new FileWriter(log.toFile())) {
      writer.write("2026-10-17 08:15:02 INFO  Server started on port 8080\n");
      System.out.println("After write(): " + Files.size(log) + " bytes");
      writer.flush();
      System.out.println("After flush(): " + Files.size(log) + " bytes");
    }

    System.out.println("== Many short writes");
    String line = "2026-10-17 08:15:02 INFO  Server started on port 8080\n";
    try (FileWriter writer = new FileWriter(log.toFile())) {
      long lastSize = 0;
      for (int i = 1; i <= 20; i++) {
        writer.write(line);
        long size = Files.size(log);
        if (size != lastSize) {
          System.out.println("After line " + i + ": " + size + " bytes in the file");
          lastSize = size;
        }
      }
    }

    System.out.println("== One long write");
    try (FileWriter writer = new FileWriter(log.toFile())) {
      writer.write("x".repeat(10_000));
      long size = Files.size(log);
      System.out.println("After 10,000 characters: " + size + " bytes in the file");
    }
  }
}
