package eu.happycoders.bufferedreader.benchmark;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * Reads the 100-million-byte test file in the ways the article compares. Each benchmark returns a
 * value computed from everything it read, so the JIT cannot drop the reading.
 *
 * <p>The file is in the page cache after the first iteration: the benchmarks measure decoding,
 * buffering and method calls, not the disk.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 2)
@Fork(3)
public class ReadBenchmark {

  private Path file;

  @Setup
  public void setUp() throws IOException {
    file = TestFile.ensureExists();
  }

  @Benchmark
  public long inputStreamReaderRead() throws IOException {
    try (InputStreamReader reader =
        new InputStreamReader(new FileInputStream(file.toFile()), UTF_8)) {
      long sum = 0;
      int c;
      while ((c = reader.read()) != -1) {
        sum += c;
      }
      return sum;
    }
  }

  @Benchmark
  public long fileReaderRead() throws IOException {
    try (FileReader reader = new FileReader(file.toFile(), UTF_8)) {
      long sum = 0;
      int c;
      while ((c = reader.read()) != -1) {
        sum += c;
      }
      return sum;
    }
  }

  @Benchmark
  public long bufferedReaderRead() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile(), UTF_8))) {
      long sum = 0;
      int c;
      while ((c = reader.read()) != -1) {
        sum += c;
      }
      return sum;
    }
  }

  @Benchmark
  public long bufferedReaderReadArray() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile(), UTF_8))) {
      char[] buffer = new char[8192];
      long sum = 0;
      int n;
      while ((n = reader.read(buffer, 0, buffer.length)) != -1) {
        for (int i = 0; i < n; i++) {
          sum += buffer[i];
        }
      }
      return sum;
    }
  }

  @Benchmark
  public long bufferedReaderReadLine() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile(), UTF_8))) {
      long sum = 0;
      String line;
      while ((line = reader.readLine()) != null) {
        sum += line.length();
      }
      return sum;
    }
  }

  @Benchmark
  public long filesNewBufferedReaderReadLine() throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(file)) {
      long sum = 0;
      String line;
      while ((line = reader.readLine()) != null) {
        sum += line.length();
      }
      return sum;
    }
  }

  @Benchmark
  public long bufferedReaderLines() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile(), UTF_8))) {
      return reader.lines().mapToLong(String::length).sum();
    }
  }

  @Benchmark
  public long bufferedReaderReadAllLines() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile(), UTF_8))) {
      long sum = 0;
      for (String line : reader.readAllLines()) {
        sum += line.length();
      }
      return sum;
    }
  }

  @Benchmark
  public long scannerNextLine() throws IOException {
    try (Scanner scanner = new Scanner(file, UTF_8)) {
      long sum = 0;
      while (scanner.hasNextLine()) {
        sum += scanner.nextLine().length();
      }
      return sum;
    }
  }
}
