package eu.happycoders.overview;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class Ch1Tasks {

  static void main() throws IOException {
    Path log = Path.of("files/server.log");
    Path dir = Path.of("target/tasks");

    System.out.println("== Read a text file into a string");
    String text = Files.readString(log);
    System.out.println(text.length() + " characters");

    System.out.println("== Read a text file line by line");
    try (Stream<String> lines = Files.lines(log)) {
      lines.filter(line -> line.contains("ERROR")).forEach(System.out::println);
    }

    System.out.println("== Read all lines into a list");
    List<String> lines = Files.readAllLines(log);
    System.out.println(lines.size() + " lines");

    System.out.println("== Create a directory, including missing parents");
    Files.createDirectories(dir);
    System.out.println(Files.isDirectory(dir));

    System.out.println("== Write a string, then append a line");
    Path out = dir.resolve("out.txt");
    Files.writeString(out, "first line\n");
    Files.writeString(out, "second line\n", StandardOpenOption.APPEND);
    System.out.print(Files.readString(out));

    System.out.println("== Read and write bytes");
    Path gz = Path.of("files/server.log.gz");
    byte[] bytes = Files.readAllBytes(gz);
    Files.write(dir.resolve("copy.gz"), bytes);
    System.out.println(bytes.length + " bytes");

    System.out.println("== Check whether a file exists");
    System.out.println(Files.exists(out));

    System.out.println("== Copy, move, delete");
    Path copy = dir.resolve("copy.txt");
    Files.copy(out, copy, StandardCopyOption.REPLACE_EXISTING);
    Path moved = dir.resolve("moved.txt");
    Files.move(copy, moved, StandardCopyOption.REPLACE_EXISTING);
    Files.delete(moved);
    System.out.println(Files.exists(copy) + " " + Files.exists(moved));

    System.out.println("== List a directory");
    try (Stream<Path> entries = Files.list(dir)) {
      entries.sorted().forEach(System.out::println);
    }
  }
}
