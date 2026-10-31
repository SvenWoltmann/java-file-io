package eu.happycoders.filereader;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch2TransferTo {

  static void main() throws IOException {
    Path latin1 = Path.of("target/greeting-latin1.txt");
    Files.writeString(latin1, "Grüße aus Köln", ISO_8859_1);

    Path utf8 = Path.of("target/greeting-utf8.txt");
    long characters;
    try (FileReader reader = new FileReader(latin1.toFile(), ISO_8859_1);
        FileWriter writer = new FileWriter(utf8.toFile(), UTF_8)) {
      characters = reader.transferTo(writer);
    }

    System.out.println(characters + " characters transferred");
    System.out.println("ISO-8859-1: " + Files.size(latin1) + " bytes");
    System.out.println("UTF-8:      " + Files.size(utf8) + " bytes");
    System.out.println(Files.readString(utf8));
  }
}
