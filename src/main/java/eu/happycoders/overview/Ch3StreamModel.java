package eu.happycoders.overview;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch3StreamModel {

  static void main() throws IOException {
    Files.writeString(Path.of("target/greeting.txt"), "Grüße\n");

    System.out.println("== InputStream: bytes");
    try (InputStream in = new FileInputStream("target/greeting.txt")) {
      int b;
      while ((b = in.read()) != -1) {
        System.out.print(b + " ");
      }
    }
    System.out.println();

    System.out.println("== Reader: characters");
    try (Reader reader = new FileReader("target/greeting.txt", UTF_8)) {
      int c;
      while ((c = reader.read()) != -1) {
        System.out.print(c + " ");
      }
    }
    System.out.println();

    System.out.println("== The chain");
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(new FileInputStream("target/greeting.txt"), UTF_8))) {
      System.out.println(reader.readLine());
    }
  }
}
