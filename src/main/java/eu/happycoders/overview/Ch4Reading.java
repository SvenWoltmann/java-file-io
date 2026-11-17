package eu.happycoders.overview;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Ch4Reading {

  static void main() throws IOException {
    Path log = Path.of("files/server.log");
    try (Stream<String> lines = Files.lines(log)) {
      Map<String, Long> levels =
          lines
              .map(line -> line.substring(20, 25).strip())
              .collect(groupingBy(level -> level, TreeMap::new, counting()));
      System.out.println(levels);
    }
  }
}
