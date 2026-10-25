package eu.happycoders.printwriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Writes the five log lines and forgets to close the PrintWriter: the lines stay in the
 * BufferedWriter's buffer, and when the program ends, the file is empty.
 */
public class Ch10Mistakes {

  static void main() throws IOException {
    Path log = Path.of("target/not-closed.log");
    PrintWriter out = new PrintWriter(Files.newBufferedWriter(log));
    for (LogEntry entry : LogEntry.SERVER_LOG) {
      out.printf("%s %-5s %s%n", entry.timestamp(), entry.level(), entry.message());
    }
    System.out.println("Bytes in the file at the end of main(): " + Files.size(log));
  }
}
