package eu.happycoders.files.benchmark;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Not a JMH benchmark: copies files of 1,000,000, 100,000,000, and 1,000,000,000 bytes with {@code
 * Files.copy()} and {@code COPY_ATTRIBUTES} and prints the median of 50 copies per size, after 10
 * copies to warm up. On APFS, the copy is a clone, so the time does not depend on the size. Like
 * {@code CopyBenchmark}, it forces each copy to disk before deleting it.
 *
 * <pre>
 * java -cp target/benchmarks.jar eu.happycoders.files.benchmark.CloneSizeCheck
 * </pre>
 */
public final class CloneSizeCheck {

  private static final Path DIR = Path.of("target/clone-size-check");
  private static final int WARMUP = 10;
  private static final int MEASURED = 50;

  private CloneSizeCheck() {}

  static void main() throws IOException {
    Files.createDirectories(DIR);
    for (long size : new long[] {1_000_000L, 100_000_000L, 1_000_000_000L}) {
      Path source = createSource(size);
      Path target = DIR.resolve("target.bin");
      List<Long> times = new ArrayList<>();
      for (int i = 0; i < WARMUP + MEASURED; i++) {
        long start = System.nanoTime();
        Files.copy(source, target, COPY_ATTRIBUTES);
        long time = System.nanoTime() - start;
        if (i >= WARMUP) {
          times.add(time);
        }
        force(target);
        Files.delete(target);
      }
      times.sort(null);
      System.out.printf(
          Locale.ROOT,
          "%,d bytes: median %d µs, min %d µs, max %d µs%n",
          size,
          times.get(times.size() / 2) / 1000,
          times.getFirst() / 1000,
          times.getLast() / 1000);
      Files.delete(source);
    }
  }

  private static Path createSource(long size) throws IOException {
    Path source = DIR.resolve("source.bin");
    byte[] block = new byte[1_000_000];
    new Random(42).nextBytes(block);
    try (OutputStream out = Files.newOutputStream(source)) {
      for (long written = 0; written < size; written += block.length) {
        out.write(block);
      }
    }
    force(source);
    return source;
  }

  private static void force(Path path) throws IOException {
    try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE)) {
      channel.force(true);
    }
  }
}
