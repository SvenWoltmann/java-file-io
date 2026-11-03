package eu.happycoders.filewriter;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileOutputStream;
import java.io.FileWriter;
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

    Path viaWriter = Path.of("target/all-bytes-writer.bin");
    try (FileWriter writer = new FileWriter(viaWriter.toFile(), UTF_8)) {
      for (byte b : allBytes) {
        writer.write(b & 0xFF); // 0 to 255 instead of -128 to 127
      }
    }
    byte[] written = Files.readAllBytes(viaWriter);
    boolean identical = Arrays.equals(written, allBytes);
    System.out.println("FileWriter:       " + written.length + " bytes, identical: " + identical);

    Path viaStream = Path.of("target/all-bytes-stream.bin");
    try (FileOutputStream out = new FileOutputStream(viaStream.toFile())) {
      out.write(allBytes);
    }
    written = Files.readAllBytes(viaStream);
    identical = Arrays.equals(written, allBytes);
    System.out.println("FileOutputStream: " + written.length + " bytes, identical: " + identical);
  }
}
