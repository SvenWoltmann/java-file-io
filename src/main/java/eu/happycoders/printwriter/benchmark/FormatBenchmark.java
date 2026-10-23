package eu.happycoders.printwriter.benchmark;

import static java.nio.charset.StandardCharsets.UTF_8;

import eu.happycoders.printwriter.benchmark.TestText.LogEntry;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * Writes 1,000,000 log lines of the form {@code 2026-10-17 08:15:02 INFO message} - the level
 * padded to five characters - to a file: once formatted with {@code printf()}, once as a string
 * built with {@code +} and written with {@code println()}, and once in pieces, with one {@code
 * print()} call per field and space, with and without a {@code BufferedWriter} below the {@code
 * PrintWriter}.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 2)
@Fork(3)
public class FormatBenchmark {

  static final int ENTRIES = 1_000_000;

  private final Path file = Path.of("target/benchmark-log.txt");
  private LogEntry[] entries;

  @Setup
  public void setUp() throws IOException {
    Files.createDirectories(file.getParent());
    entries = TestText.logEntries(ENTRIES);
  }

  @Benchmark
  public void printf() throws IOException {
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      for (LogEntry e : entries) {
        out.printf("%s %-5s %s%n", e.timestamp(), e.level(), e.message());
      }
    }
  }

  @Benchmark
  public void printlnConcatenated() throws IOException {
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      for (LogEntry e : entries) {
        out.println(
            e.timestamp() + " " + e.level() + " ".repeat(6 - e.level().length()) + e.message());
      }
    }
  }

  @Benchmark
  public void printPieces() throws IOException {
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      for (LogEntry e : entries) {
        printPieces(out, e);
      }
    }
  }

  @Benchmark
  public void printPiecesAroundFileWriter() throws IOException {
    try (PrintWriter out = new PrintWriter(new FileWriter(file.toFile(), UTF_8))) {
      for (LogEntry e : entries) {
        printPieces(out, e);
      }
    }
  }

  private static void printPieces(PrintWriter out, LogEntry e) {
    out.print(e.timestamp());
    out.print(' ');
    out.print(e.level());
    for (int i = e.level().length(); i < 6; i++) {
      out.print(' ');
    }
    out.println(e.message());
  }
}
