package eu.happycoders.overview;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class Ch5Writing {

  static void main() throws IOException {
    Map<String, Long> levels = new TreeMap<>(Map.of("ERROR", 1L, "INFO", 3L, "WARN", 1L));

    Path report = Path.of("target/report.txt");
    try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(report))) {
      levels.forEach((level, count) -> writer.printf("%-5s %d%n", level, count));
    }
    System.out.print(Files.readString(report));
  }
}
