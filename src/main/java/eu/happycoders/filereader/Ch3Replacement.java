package eu.happycoders.filereader;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch3Replacement {

  static void main() throws IOException {
    Path latin1 = Path.of("target/greeting-latin1.txt");
    Files.writeString(latin1, "Grüße aus Köln", ISO_8859_1);

    System.out.println("== FileReader");
    try (FileReader reader = new FileReader(latin1.toFile(), UTF_8)) {
      String text = reader.readAllAsString();
      System.out.println(text);
      System.out.println("Contains U+FFFD: " + (text.indexOf(0xFFFD) >= 0));
    }

    System.out.println("== InputStreamReader with a reporting decoder");
    CharsetDecoder decoder =
        UTF_8
            .newDecoder()
            .onMalformedInput(CodingErrorAction.REPORT)
            .onUnmappableCharacter(CodingErrorAction.REPORT);
    try (InputStreamReader reader =
        new InputStreamReader(new FileInputStream(latin1.toFile()), decoder)) {
      System.out.println(reader.readAllAsString());
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
