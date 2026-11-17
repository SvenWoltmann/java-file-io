package eu.happycoders.overview;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Intro {

  static void main() throws IOException {
    Path file = Path.of("target/greeting.txt");
    Files.writeString(file, "Grüße aus Java\n");
    String text = Files.readString(file);
    System.out.print(text);
  }
}
