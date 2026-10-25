package eu.happycoders.bufferedreader.benchmark;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Locale;

/**
 * Reads the test file line by line with different BufferedReader buffer sizes and counts how often
 * the InputStreamReader reads from the file. The count is the same for every buffer size: the
 * InputStreamReader fills its own 8,192-byte buffer, no matter how many characters the
 * BufferedReader asks for.
 */
public class ReadCallCounter {

  static void main() throws IOException {
    Path file = TestFile.ensureExists();
    for (int bufferSize : new int[] {256, 1_024, 8_192, 65_536, 1_048_576}) {
      CountingInputStream in = new CountingInputStream(new FileInputStream(file.toFile()));
      long lines;
      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(in, UTF_8), bufferSize)) {
        lines = reader.lines().count();
      }
      System.out.printf(
          Locale.ROOT,
          "buffer %,9d chars: %,d lines, %,d reads from the file, %,.1f bytes per read%n",
          bufferSize,
          lines,
          in.reads,
          (double) in.bytes / in.reads);
    }
  }

  private static class CountingInputStream extends FilterInputStream {
    private long reads;
    private long bytes;

    CountingInputStream(InputStream in) {
      super(in);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
      int n = super.read(b, off, len);
      if (n > 0) {
        reads++;
        bytes += n;
      }
      return n;
    }
  }
}
