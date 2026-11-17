package eu.happycoders.overview;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Ch2TwoApis {

  static void main() {
    System.out.println("== File.delete()");
    File file = new File("target/missing.txt");
    boolean deleted = file.delete();
    System.out.println("deleted = " + deleted);

    System.out.println("== Files.delete()");
    Path path = Path.of("target/missing.txt");
    try {
      Files.delete(path);
    } catch (IOException e) {
      System.out.println(e);
    }

    System.out.println("== File.listFiles() on a file");
    File[] children = new File("files/server.log").listFiles();
    System.out.println("children = " + children);

    System.out.println("== Files.list() on a file");
    try (Stream<Path> entries = Files.list(Path.of("files/server.log"))) {
      System.out.println(entries.count());
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
