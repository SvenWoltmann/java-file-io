package eu.happycoders.printwriter.benchmark;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
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
 * Writes the 1,222,747 lines of the test text - 100,000,000 bytes as UTF-8 - to a file, once per
 * way of writing the article compares. Every benchmark creates the file anew and closes it at the
 * end; it measures up to the close, not until the bytes are on the SSD.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 2)
@Fork(3)
public class WriteBenchmark {

  private final Path file = Path.of("target/benchmark-output.txt");
  private String[] lines;

  @Setup
  public void setUp() throws IOException {
    Files.createDirectories(file.getParent());
    lines = TestText.lines();
  }

  @Benchmark
  public void bufferedWriter() throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(file)) {
      for (String line : lines) {
        writer.write(line);
        writer.newLine();
      }
    }
  }

  @Benchmark
  public void printWriterAroundBufferedWriter() throws IOException {
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
      for (String line : lines) {
        out.println(line);
      }
    }
  }

  @Benchmark
  public void printWriterFileName() throws IOException {
    try (PrintWriter out = new PrintWriter(file.toFile(), UTF_8)) {
      for (String line : lines) {
        out.println(line);
      }
    }
  }

  @Benchmark
  public void printWriterAroundFileWriter() throws IOException {
    try (PrintWriter out = new PrintWriter(new FileWriter(file.toFile(), UTF_8))) {
      for (String line : lines) {
        out.println(line);
      }
    }
  }

  /**
   * The chain of a FileWriter - an OutputStreamWriter around a FileOutputStream - with a
   * BufferedOutputStream in between: every call is still encoded on its own, but the bytes reach
   * the file in blocks of 8,192 bytes instead of 512.
   */
  @Benchmark
  public void printWriterAroundEncoderWithBufferedStream() throws IOException {
    try (PrintWriter out =
        new PrintWriter(
            new OutputStreamWriter(
                new BufferedOutputStream(new FileOutputStream(file.toFile())), UTF_8))) {
      for (String line : lines) {
        out.println(line);
      }
    }
  }

  @Benchmark
  public void printWriterAutoFlush() throws IOException {
    try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file), true)) {
      for (String line : lines) {
        out.println(line);
      }
    }
  }

  @Benchmark
  public void printStream() throws IOException {
    try (PrintStream out =
        new PrintStream(new BufferedOutputStream(Files.newOutputStream(file)), false, UTF_8)) {
      for (String line : lines) {
        out.println(line);
      }
    }
  }
}
