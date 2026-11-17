package eu.happycoders.overview;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Ch6Exceptions {

  static void main() throws IOException {
    System.out.println("== A file that does not exist");
    try {
      Files.readString(Path.of("files/missing.log"));
    } catch (IOException e) {
      System.out.println(e);
    }

    System.out.println("== A directory instead of a file");
    try {
      Files.readString(Path.of("files"));
    } catch (IOException e) {
      System.out.println(e);
    }

    System.out.println("== A Latin-1 file read as UTF-8");
    Path latin1 = Path.of("target/latin1.txt");
    Files.writeString(latin1, "line 1\nGrüße\nline 3\n", ISO_8859_1);
    try (Stream<String> lines = Files.lines(latin1)) {
      lines.forEach(System.out::println);
    } catch (UncheckedIOException e) {
      System.out.println(e);
    }
  }
}
