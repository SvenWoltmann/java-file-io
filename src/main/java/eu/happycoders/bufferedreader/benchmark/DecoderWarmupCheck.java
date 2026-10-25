package eu.happycoders.bufferedreader.benchmark;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

/**
 * Shows that the speed of decoding UTF-8 with an {@link InputStreamReader} depends on the block
 * size the JVM was warmed up with, not on the block size it then decodes. It warms up with one
 * block size (15 reads of the whole test file), then measures 15 reads with another and prints the
 * median. Not a JMH benchmark: every configuration needs a JVM of its own, so start one per call:
 *
 * <pre>
 * java -cp target/classes eu.happycoders.bufferedreader.benchmark.DecoderWarmupCheck utf8 1024 8192
 * </pre>
 *
 * <p>The first argument selects the file: {@code utf8} is the benchmark test file, {@code ascii} a
 * copy in which every non-ASCII character is replaced by {@code x}.
 */
public class DecoderWarmupCheck {

  private static final int ROUNDS = 15;

  static void main(String[] args) throws IOException {
    Path file = args[0].equals("ascii") ? asciiCopy() : TestFile.ensureExists();
    int warmupBlockSize = Integer.parseInt(args[1]);
    int measuredBlockSize = Integer.parseInt(args[2]);

    long sink = 0;
    for (int i = 0; i < ROUNDS; i++) {
      sink += decode(file, warmupBlockSize);
    }
    long[] nanos = new long[ROUNDS];
    for (int i = 0; i < ROUNDS; i++) {
      long start = System.nanoTime();
      sink += decode(file, measuredBlockSize);
      nanos[i] = System.nanoTime() - start;
    }
    Arrays.sort(nanos);
    System.out.printf(
        Locale.ROOT,
        "%s, warm-up blocks %,d, measured blocks %,d: %.1f ms (checksum %d)%n",
        args[0],
        warmupBlockSize,
        measuredBlockSize,
        nanos[ROUNDS / 2] / 1e6,
        sink % 1000);
  }

  private static long decode(Path file, int blockSize) throws IOException {
    long sum = 0;
    try (Reader reader = new InputStreamReader(new FileInputStream(file.toFile()), UTF_8)) {
      char[] block = new char[blockSize];
      int n;
      while ((n = reader.read(block, 0, blockSize)) != -1) {
        sum += n + block[0];
      }
    }
    return sum;
  }

  private static Path asciiCopy() throws IOException {
    Path ascii = Path.of("target/benchmark-input-ascii.txt");
    if (!Files.exists(ascii)) {
      String text = Files.readString(TestFile.ensureExists());
      Files.writeString(ascii, text.replaceAll("[^\\x00-\\x7F]", "x"), UTF_8);
    }
    return ascii;
  }
}
