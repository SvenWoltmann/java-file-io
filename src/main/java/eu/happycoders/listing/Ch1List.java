package eu.happycoders.listing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Ch1List {

  static void main() throws IOException {
    Path project = Path.of("files/project");

    System.out.println("== list(project)");
    try (Stream<Path> entries = Files.list(project)) {
      entries.forEach(System.out::println);
    }

    System.out.println("== list(project/src)");
    try (Stream<Path> entries = Files.list(project.resolve("src"))) {
      entries.forEach(System.out::println);
    }

    System.out.println("== list(project).sorted()");
    try (Stream<Path> entries = Files.list(project)) {
      entries.sorted().forEach(System.out::println);
    }

    System.out.println("== only directories");
    try (Stream<Path> entries = Files.list(project)) {
      entries.filter(Files::isDirectory).forEach(System.out::println);
    }

    System.out.println("== list() on a file and on a missing directory");
    try (Stream<Path> entries = Files.list(project.resolve("pom.xml"))) {
      entries.forEach(System.out::println);
    } catch (NotDirectoryException e) {
      System.out.println(e);
    }
    try (Stream<Path> entries = Files.list(project.resolve("test"))) {
      entries.forEach(System.out::println);
    } catch (NoSuchFileException e) {
      System.out.println(e);
    }
  }
}
