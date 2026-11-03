package eu.happycoders.filewriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch8Closing {

  static void main() throws IOException, InterruptedException {
    System.out.println("== A FileWriter that is never closed");
    Path log = Path.of("target/not-closed.log");
    FileWriter writer = new FileWriter(log.toFile());
    writer.write("2026-10-17 08:15:02 INFO  Server started on port 8080\n");
    System.out.println("After write():              " + Files.size(log) + " bytes");
    writer = null;
    System.gc();
    Thread.sleep(100);
    System.out.println("After a garbage collection: " + Files.size(log) + " bytes");

    System.out.println("== Writing after close()");
    FileWriter closed = new FileWriter("target/closed.log");
    closed.close();
    try {
      closed.write("too late");
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
