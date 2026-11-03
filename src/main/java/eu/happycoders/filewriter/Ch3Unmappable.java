package eu.happycoders.filewriter;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HexFormat;

public class Ch3Unmappable {

  static void main() throws IOException {
    System.out.println("== FileWriter");
    Path price = Path.of("target/price.txt");
    try (FileWriter writer = new FileWriter(price.toFile(), ISO_8859_1)) {
      writer.write("Preis: 5 €");
    }
    System.out.println(Files.readString(price, ISO_8859_1));

    System.out.println("== OutputStreamWriter with a reporting encoder");
    CharsetEncoder encoder =
        ISO_8859_1
            .newEncoder()
            .onMalformedInput(CodingErrorAction.REPORT)
            .onUnmappableCharacter(CodingErrorAction.REPORT);
    Path reported = Path.of("target/price-reported.txt");
    try (OutputStreamWriter writer =
        new OutputStreamWriter(new FileOutputStream(reported.toFile()), encoder)) {
      writer.write("Preis: 5 €");
    } catch (IOException e) {
      System.out.println(e);
    }
    String content = Files.readString(reported, ISO_8859_1);
    System.out.println("In the file: \"" + content + "\"");

    System.out.println("== Files.newBufferedWriter() and Files.writeString()");
    try (BufferedWriter writer = Files.newBufferedWriter(reported, ISO_8859_1)) {
      writer.write("Preis: 5 €");
    } catch (IOException e) {
      System.out.println(e);
    }
    try {
      Files.writeString(reported, "Preis: 5 €", ISO_8859_1);
    } catch (IOException e) {
      System.out.println(e);
    }

    System.out.println("== Half a surrogate pair");
    String message = "Start 🚀";
    String truncated = message.substring(0, 7);
    Path half = Path.of("target/half-rocket.txt");
    try (FileWriter writer = new FileWriter(half.toFile(), UTF_8)) {
      writer.write(truncated);
    }
    String hex = HexFormat.ofDelimiter(" ").formatHex(Files.readAllBytes(half));
    System.out.println(Files.readString(half) + "  " + hex);
    try {
      Files.writeString(half, truncated);
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
