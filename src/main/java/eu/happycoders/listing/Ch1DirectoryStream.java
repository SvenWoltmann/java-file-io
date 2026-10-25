package eu.happycoders.listing;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch1DirectoryStream {

  static void main() throws IOException {
    Path project = Path.of("files/project");

    System.out.println("== newDirectoryStream(project, \"*.md\")");
    try (DirectoryStream<Path> entries = Files.newDirectoryStream(project, "*.md")) {
      for (Path entry : entries) {
        System.out.println(entry);
      }
    }

    System.out.println("== newDirectoryStream(project, \"*.{md,xml}\")");
    try (DirectoryStream<Path> entries = Files.newDirectoryStream(project, "*.{md,xml}")) {
      for (Path entry : entries) {
        System.out.println(entry);
      }
    }
  }
}
