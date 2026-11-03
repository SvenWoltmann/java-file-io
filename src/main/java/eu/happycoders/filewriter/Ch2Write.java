package eu.happycoders.filewriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch2Write {

  static void main() throws IOException {
    System.out.println("== write(int) with two halves of a surrogate pair");
    Path rocket = Path.of("target/rocket.txt");
    try (FileWriter writer = new FileWriter(rocket.toFile())) {
      writer.write(0xD83D);
      writer.write(0xDE80);
    }
    System.out.println(Files.readString(rocket) + " " + Files.size(rocket) + " bytes");

    System.out.println("== write(int) with a number");
    try (FileWriter writer = new FileWriter("target/status.txt")) {
      writer.write(200);
    }
    System.out.println(Files.readString(Path.of("target/status.txt")));

    System.out.println("== write(int) with a code point");
    Path codePoint = Path.of("target/code-point.txt");
    try (FileWriter writer = new FileWriter(codePoint.toFile())) {
      writer.write("🚀".codePointAt(0)); // 0x1F680
    }
    char c = Files.readString(codePoint).charAt(0);
    System.out.println(Integer.toHexString(c) + " " + Character.getName(c));

    System.out.println("== write(String) and append()");
    String path = "/java/java-filewriter/";
    int code = 200;
    String referrer = null;
    try (FileWriter writer = new FileWriter("target/request.log")) {
      writer.write("GET ");
      writer.write(path);
      writer.append(' ').append(String.valueOf(code));
      writer.append(" referrer=").append(referrer).append('\n');
    }
    System.out.print(Files.readString(Path.of("target/request.log")));

    System.out.println("== write(String) with null");
    try (FileWriter writer = new FileWriter("target/request.log")) {
      String nothing = null;
      writer.write(nothing);
    } catch (NullPointerException e) {
      System.out.println(e);
    }
  }
}
