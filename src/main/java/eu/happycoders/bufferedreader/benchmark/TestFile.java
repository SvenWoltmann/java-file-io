package eu.happycoders.bufferedreader.benchmark;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * Creates the text file the benchmarks read: 100,000,000 bytes of UTF-8 text in lines of random
 * length (0 to 160 characters, 80 on average). The words are random lowercase letters; one
 * character in a hundred is a German umlaut, so the decoder sees two-byte sequences as well.
 *
 * <p>The seed is fixed, so every machine reads the same file.
 */
final class TestFile {

  static final Path PATH = Path.of("target/benchmark-input.txt");
  static final long SIZE = 100_000_000;

  private static final long SEED = 20261017;
  private static final String UMLAUTS = "äöüß";

  private TestFile() {}

  static Path ensureExists() throws IOException {
    if (Files.exists(PATH) && Files.size(PATH) == SIZE) {
      return PATH;
    }
    Files.createDirectories(PATH.getParent());
    RandomGenerator random = RandomGeneratorFactory.of("L64X128MixRandom").create(SEED);
    long bytes = 0;
    try (BufferedWriter writer = Files.newBufferedWriter(PATH, UTF_8)) {
      while (bytes < SIZE) {
        StringBuilder line = new StringBuilder();
        int lineBytes = 0;
        int length = random.nextInt(161);
        while (line.length() < length) {
          int r = random.nextInt(100);
          if (r == 0) {
            line.append(UMLAUTS.charAt(random.nextInt(UMLAUTS.length())));
            lineBytes += 2;
          } else if (r < 15) {
            line.append(' ');
            lineBytes++;
          } else {
            line.append((char) ('a' + random.nextInt(26)));
            lineBytes++;
          }
        }
        // The last line is cut so that the file has exactly SIZE bytes.
        long remaining = SIZE - bytes - 1;
        if (lineBytes > remaining) {
          line.setLength(0);
          for (long i = 0; i < remaining; i++) {
            line.append('x');
          }
          lineBytes = (int) remaining;
        }
        writer.write(line.toString());
        writer.write('\n');
        bytes += lineBytes + 1;
      }
    }
    return PATH;
  }
}
