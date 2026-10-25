package eu.happycoders.listing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Ch5Find {

  static void main() throws IOException {
    Path project = Path.of("files/project");

    System.out.println("== find(): .java files");
    try (Stream<Path> matches =
        Files.find(
            project,
            Integer.MAX_VALUE,
            (path, attrs) -> attrs.isRegularFile() && path.toString().endsWith(".java"))) {
      matches.forEach(System.out::println);
    }

    System.out.println("== find(): files larger than 120 bytes");
    try (Stream<Path> matches =
        Files.find(
            project,
            Integer.MAX_VALUE,
            (path, attrs) -> attrs.isRegularFile() && attrs.size() > 120)) {
      matches.forEach(System.out::println);
    }

    System.out.println("== find() with maxDepth 1: directories");
    try (Stream<Path> matches = Files.find(project, 1, (path, attrs) -> attrs.isDirectory())) {
      matches.forEach(System.out::println);
    }
  }
}
