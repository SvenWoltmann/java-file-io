package eu.happycoders.filereader;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Ch4InputStreamReader {

  static void main() throws IOException {
    System.out.println("== A resource from the classpath");
    InputStream in = Ch4InputStreamReader.class.getResourceAsStream("greeting.txt");
    try (InputStreamReader reader = new InputStreamReader(in, UTF_8)) {
      System.out.print(reader.readAllAsString());
    }

    System.out.println("== An unknown charset name");
    try {
      new InputStreamReader(InputStream.nullInputStream(), "UTF-9");
    } catch (UnsupportedEncodingException e) {
      System.out.println(e);
    }
  }
}
