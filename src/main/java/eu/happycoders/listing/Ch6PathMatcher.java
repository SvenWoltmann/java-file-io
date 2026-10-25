package eu.happycoders.listing;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

public class Ch6PathMatcher {

  static void main() throws IOException {
    Path project = Path.of("files/project");

    System.out.println("== glob:**/*.java");
    PathMatcher javaFiles = FileSystems.getDefault().getPathMatcher("glob:**/*.java");
    try (Stream<Path> paths = Files.walk(project)) {
      paths.filter(javaFiles::matches).forEach(System.out::println);
    }

    System.out.println("== glob:*.{md,xml} on the file name");
    PathMatcher docs = FileSystems.getDefault().getPathMatcher("glob:*.{md,xml}");
    try (Stream<Path> paths = Files.walk(project)) {
      paths.filter(path -> docs.matches(path.getFileName())).forEach(System.out::println);
    }

    System.out.println("== regex:.*/src/.*");
    PathMatcher inSrc = FileSystems.getDefault().getPathMatcher("regex:.*/src/.*");
    try (Stream<Path> paths = Files.walk(project)) {
      paths.filter(inSrc::matches).forEach(System.out::println);
    }
  }
}
