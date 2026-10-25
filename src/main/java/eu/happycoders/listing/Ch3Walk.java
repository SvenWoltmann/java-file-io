package eu.happycoders.listing;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Ch3Walk {

  static void main() throws IOException {
    Path project = Path.of("files/project");

    System.out.println("== walk(project)");
    try (Stream<Path> paths = Files.walk(project)) {
      paths.forEach(System.out::println);
    }

    System.out.println("== walk(project): .java files");
    try (Stream<Path> paths = Files.walk(project)) {
      paths
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().endsWith(".java"))
          .forEach(System.out::println);
    }

    System.out.println("== walk(project, 1)");
    try (Stream<Path> paths = Files.walk(project, 1)) {
      paths.forEach(System.out::println);
    }

    System.out.println("== walk(project, 0)");
    try (Stream<Path> paths = Files.walk(project, 0)) {
      paths.forEach(System.out::println);
    }

    Path links = Path.of("target/links");
    if (Files.exists(links)) {
      Examples.deleteDirectory(links);
    }
    Files.createDirectories(links.resolve("data"));
    Files.writeString(links.resolve("data/a.txt"), "a\n");
    Files.createSymbolicLink(links.resolve("shortcut"), Path.of("data"));

    System.out.println("== walk(links)");
    try (Stream<Path> paths = Files.walk(links)) {
      paths.forEach(System.out::println);
    }

    System.out.println("== walk(links, FOLLOW_LINKS)");
    try (Stream<Path> paths = Files.walk(links, FOLLOW_LINKS)) {
      paths.forEach(System.out::println);
    }

    System.out.println("== walk(links, FOLLOW_LINKS) with a loop");
    Files.createSymbolicLink(links.resolve("data/loop"), Path.of(".."));
    try (Stream<Path> paths = Files.walk(links, FOLLOW_LINKS)) {
      paths.forEach(System.out::println);
    } catch (UncheckedIOException e) {
      System.out.println(e.getCause());
    }
  }
}
