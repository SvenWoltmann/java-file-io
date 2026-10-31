package eu.happycoders.filereader;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class Ch1Creating {

  static void main() throws IOException {
    System.out.println("== String, File, and Path");
    Path log = Path.of("files/server.log");
    try (FileReader fromName = new FileReader("files/server.log");
        FileReader fromFile = new FileReader(new File("files/server.log"));
        FileReader fromPath = new FileReader(log.toFile())) {
      // all three read the same file
      System.out.println(
          "" + (char) fromName.read() + (char) fromFile.read() + (char) fromPath.read());
    }

    System.out.println("== getEncoding()");
    try (FileReader reader = new FileReader("files/server.log")) {
      System.out.println("Without charset: " + reader.getEncoding());
    }
    try (FileReader reader = new FileReader("files/server.log", UTF_8)) {
      System.out.println("UTF_8:           " + reader.getEncoding());
    }
    try (FileReader reader = new FileReader("files/server.log", ISO_8859_1)) {
      System.out.println("ISO_8859_1:      " + reader.getEncoding());
    }
    try (FileReader reader = new FileReader("files/server.log", Charset.forName("windows-1252"))) {
      System.out.println("windows-1252:    " + reader.getEncoding());
    }
    FileReader closed = new FileReader("files/server.log");
    closed.close();
    System.out.println("After close():   " + closed.getEncoding());

    System.out.println("== Files that cannot be opened");
    for (String fileName : new String[] {"files/missing.log", "files"}) {
      try (FileReader reader = new FileReader(fileName, UTF_8)) {
        System.out.println(reader.read());
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}
