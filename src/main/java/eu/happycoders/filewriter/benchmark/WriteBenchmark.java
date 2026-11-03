package eu.happycoders.filewriter.benchmark;

import eu.happycoders.printwriter.benchmark.TestText;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
 * Writes the 1,222,747 lines of the test text - 100,000,000 bytes as UTF-8, the text of the
 * PrintWriter benchmarks - to a file with a {@link FileWriter}: character by character, line by
 * line, and in blocks of 8,192 characters, and with a {@link BufferedWriter} around the {@code
 * FileWriter} character by character and line by line. Every benchmark creates the file anew and
 * closes it at the end; it measures up to the close, not until the bytes are on the SSD.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 2)
@Fork(3)
public class WriteBenchmark {

  private static final int BLOCK_SIZE = 8192;

  private final Path file = Path.of("target/benchmark-output.txt");
  private String[] lines;
  private char[] text;

  @Setup
  public void setUp() throws IOException {
    Files.createDirectories(file.getParent());
    lines = TestText.lines();
    StringBuilder all = new StringBuilder();
    for (String line : lines) {
      all.append(line).append('\n');
    }
    text = all.toString().toCharArray();
  }

  @Benchmark
  public void fileWriterWriteChar() throws IOException {
    try (FileWriter writer = new FileWriter(file.toFile())) {
      for (char c : text) {
        writer.write(c);
      }
    }
  }

  @Benchmark
  public void fileWriterWriteLine() throws IOException {
    try (FileWriter writer = new FileWriter(file.toFile())) {
      for (String line : lines) {
        writer.write(line);
        writer.write('\n');
      }
    }
  }

  @Benchmark
  public void fileWriterWriteBlock() throws IOException {
    try (FileWriter writer = new FileWriter(file.toFile())) {
      for (int offset = 0; offset < text.length; offset += BLOCK_SIZE) {
        writer.write(text, offset, Math.min(BLOCK_SIZE, text.length - offset));
      }
    }
  }

  @Benchmark
  public void bufferedWriterWriteChar() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()))) {
      for (char c : text) {
        writer.write(c);
      }
    }
  }

  @Benchmark
  public void bufferedWriterWriteLine() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()))) {
      for (String line : lines) {
        writer.write(line);
        writer.newLine();
      }
    }
  }
}
