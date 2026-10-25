package eu.happycoders.listing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Intro {

  static void main() throws IOException {
    try (Stream<Path> entries = Files.list(Path.of("files/project"))) {
      entries.forEach(System.out::println);
    }
  }
}
