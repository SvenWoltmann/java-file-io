package eu.happycoders.filewriter;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch1Creating {

  static void main() throws IOException {
    System.out.println("== String, File, and Path");
    Path path = Path.of("target/from-path.txt");
    try (FileWriter fromName = new FileWriter("target/from-name.txt");
        FileWriter fromFile = new FileWriter(new File("target/from-file.txt"));
        FileWriter fromPath = new FileWriter(path.toFile())) {
      // each one writes into its own file
      fromName.write("name");
      fromFile.write("file");
      fromPath.write("path");
    }
    System.out.println(Files.readString(path));

    System.out.println("== Overwrite or append");
    Path log = Path.of("target/server.log");
    Files.writeString(log, "2026-10-17 08:15:02 INFO  Server started on port 8080\n");
    System.out.println("At the start:           " + Files.size(log) + " bytes");
    try (FileWriter writer = new FileWriter(log.toFile(), true)) {
      writer.write("2026-10-17 08:15:12 ERROR Connection reset by peer\n");
    }
    System.out.println("After append = true:    " + Files.size(log) + " bytes");
    new FileWriter(log.toFile()).close();
    System.out.println("After new FileWriter(): " + Files.size(log) + " bytes");

    System.out.println("== Appending to a file that does not exist");
    Path missing = Path.of("target/appended.log");
    Files.deleteIfExists(missing);
    try (FileWriter writer = new FileWriter(missing.toFile(), true)) {
      writer.write("first line\n");
    }
    System.out.println("Exists: " + Files.exists(missing) + ", " + Files.size(missing) + " bytes");

    System.out.println("== getEncoding()");
    try (FileWriter writer = new FileWriter("target/encoding.txt")) {
      System.out.println("Without charset: " + writer.getEncoding());
    }
    try (FileWriter writer = new FileWriter("target/encoding.txt", ISO_8859_1)) {
      System.out.println("ISO_8859_1:      " + writer.getEncoding());
    }
    FileWriter closed = new FileWriter("target/encoding.txt", UTF_8);
    closed.close();
    System.out.println("After close():   " + closed.getEncoding());

    System.out.println("== Files that cannot be created");
    File readOnly = new File("target/read-only.txt");
    readOnly.delete();
    readOnly.createNewFile();
    readOnly.setReadOnly();
    for (String fileName :
        new String[] {"target/missing-dir/out.txt", "target", "target/read-only.txt"}) {
      try (FileWriter writer = new FileWriter(fileName, UTF_8)) {
        writer.write("x");
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}
