package eu.happycoders.listing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Ch1OpenStreams {

  static void main() throws IOException {
    Path project = Path.of("files/project");
    List<Stream<Path>> open = new ArrayList<>();
    try {
      while (true) {
        open.add(Files.list(project)); // never closed
      }
    } catch (IOException e) {
      System.out.println(open.size() + " streams open, then: " + e);
    } finally {
      for (Stream<Path> stream : open) {
        stream.close();
      }
    }
  }
}
