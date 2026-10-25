package eu.happycoders.bufferedreader;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Ch3Lines {

  static void main() throws IOException {
    Path log = Path.of("files/server.log");

    System.out.println("== Filtering lines");
    try (BufferedReader reader = Files.newBufferedReader(log)) {
      reader.lines().filter(line -> line.contains(" GET ")).forEach(System.out::println);
    }

    System.out.println("== Counting lines per log level");
    try (BufferedReader reader = Files.newBufferedReader(log)) {
      Map<String, Long> linesPerLevel =
          reader
              .lines()
              .collect(
                  groupingBy(line -> line.substring(20, 25).strip(), TreeMap::new, counting()));
      System.out.println(linesPerLevel);
    }

    System.out.println("== An IOException while streaming");
    Path latin1 = Path.of("target/greeting-latin1.txt");
    Files.writeString(latin1, "Grüße aus Köln", ISO_8859_1);
    try (BufferedReader reader = Files.newBufferedReader(latin1)) {
      long count = reader.lines().count();
      System.out.println(count + " lines");
    } catch (UncheckedIOException e) {
      System.out.println(e + "\n  caused by " + e.getCause());
    }

    System.out.println("== Files.lines()");
    try (Stream<String> lines = Files.lines(log)) {
      lines.filter(line -> line.contains(" GET ")).forEach(System.out::println);
    }
  }
}
