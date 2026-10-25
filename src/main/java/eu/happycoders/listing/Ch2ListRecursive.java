package eu.happycoders.listing;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Ch2ListRecursive {

  static void main() throws IOException {
    findJavaFiles(Path.of("files/project"));
  }

  private static void findJavaFiles(Path dir) throws IOException {
    try (Stream<Path> entries = Files.list(dir)) {
      entries.forEach(
          entry -> {
            if (Files.isRegularFile(entry) && entry.toString().endsWith(".java")) {
              System.out.println(entry);
            }
            if (Files.isDirectory(entry)) {
              try {
                findJavaFiles(entry);
              } catch (IOException e) {
                throw new UncheckedIOException(e);
              }
            }
          });
    }
  }
}
