package eu.happycoders.filereader;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Ch6Binary {

  static void main() throws IOException {
    byte[] allBytes = new byte[256];
    for (int i = 0; i < allBytes.length; i++) {
      allBytes[i] = (byte) i;
    }
    Path binary = Path.of("target/all-bytes.bin");
    Files.write(binary, allBytes);

    System.out.println("== FileReader");
    try (FileReader reader = new FileReader(binary.toFile(), UTF_8)) {
      String text = reader.readAllAsString();
      long replaced = text.chars().filter(c -> c == 0xFFFD).count();
      System.out.println(text.length() + " characters, " + replaced + " of them U+FFFD");
      System.out.println("Back to bytes: " + text.getBytes(UTF_8).length + " bytes");
    }

    System.out.println("== FileInputStream");
    try (FileInputStream in = new FileInputStream(binary.toFile())) {
      byte[] bytes = in.readAllBytes();
      boolean identical = Arrays.equals(bytes, allBytes);
      System.out.println(bytes.length + " bytes, identical: " + identical);
    }
  }
}
