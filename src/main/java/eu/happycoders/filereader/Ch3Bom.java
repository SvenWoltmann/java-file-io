package eu.happycoders.filereader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch3Bom {

  static void main() throws IOException {
    Path withBom = Path.of("target/bom.csv");
    byte[] bom = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    Files.write(withBom, bom);
    Files.writeString(withBom, "id;name\n1;Grüße\n", UTF_8, APPEND);

    try (FileReader reader = new FileReader(withBom.toFile(), UTF_8)) {
      String text = reader.readAllAsString();
      String header = text.lines().findFirst().orElseThrow();
      System.out.println("Header:            " + header);
      System.out.println("Length:            " + header.length());
      System.out.println(
          "First character:   U+" + Integer.toHexString(header.charAt(0)).toUpperCase());
      System.out.println("equals(\"id;name\"): " + header.equals("id;name"));

      if (!text.isEmpty() && text.charAt(0) == 0xFEFF) {
        text = text.substring(1);
      }
      String firstLine = text.lines().findFirst().orElseThrow();
      System.out.println("Without BOM:       " + firstLine.equals("id;name"));
    }
  }
}
