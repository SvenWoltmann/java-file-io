package eu.happycoders.printwriter.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * Creates the text the benchmarks write, in memory.
 *
 * <p>{@link #lines()} produces the lines of the test file that {@code
 * eu.happycoders.bufferedreader.benchmark.ReadBenchmark} reads: the same generator, the same seed,
 * so written with a {@code '\n'} after every line, they make up the same 100,000,000 bytes of UTF-8
 * text. The lines are 0 to 160 characters long, 80 on average; one character in a hundred is a
 * German umlaut.
 *
 * <p>{@link #logEntries(int)} produces log entries with a timestamp, a level and a message for the
 * benchmarks that format.
 */
final class TestText {

  static final long FILE_SIZE = 100_000_000;

  private static final long SEED = 20261017;
  private static final String UMLAUTS = "äöüß";
  private static final String[] LEVELS = {"INFO", "INFO", "INFO", "WARN", "ERROR", "DEBUG"};

  private TestText() {}

  record LogEntry(String timestamp, String level, String message) {}

  static String[] lines() {
    RandomGenerator random = RandomGeneratorFactory.of("L64X128MixRandom").create(SEED);
    List<String> lines = new ArrayList<>();
    long bytes = 0;
    while (bytes < FILE_SIZE) {
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
      // The last line is cut so that the text has exactly FILE_SIZE bytes.
      long remaining = FILE_SIZE - bytes - 1;
      if (lineBytes > remaining) {
        line.setLength(0);
        for (long i = 0; i < remaining; i++) {
          line.append('x');
        }
        lineBytes = (int) remaining;
      }
      lines.add(line.toString());
      bytes += lineBytes + 1;
    }
    return lines.toArray(String[]::new);
  }

  static LogEntry[] logEntries(int count) {
    RandomGenerator random = RandomGeneratorFactory.of("L64X128MixRandom").create(SEED);
    LogEntry[] entries = new LogEntry[count];
    int secondOfDay = 8 * 3600;
    for (int i = 0; i < count; i++) {
      secondOfDay = (secondOfDay + random.nextInt(3)) % 86_400;
      String timestamp =
          "2026-10-17 %02d:%02d:%02d"
              .formatted(secondOfDay / 3600, secondOfDay / 60 % 60, secondOfDay % 60);
      String level = LEVELS[random.nextInt(LEVELS.length)];
      StringBuilder message = new StringBuilder();
      int length = 20 + random.nextInt(41);
      while (message.length() < length) {
        message.append(random.nextInt(7) == 0 ? ' ' : (char) ('a' + random.nextInt(26)));
      }
      entries[i] = new LogEntry(timestamp, level, message.toString());
    }
    return entries;
  }
}
