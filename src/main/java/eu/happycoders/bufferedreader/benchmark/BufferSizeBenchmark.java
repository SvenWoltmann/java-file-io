package eu.happycoders.bufferedreader.benchmark;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * Reads the test file line by line with different sizes of the BufferedReader's character buffer.
 * 8,192 is the default.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 2)
@Fork(3)
public class BufferSizeBenchmark {

  @Param({"256", "1024", "8192", "65536", "1048576"})
  private int bufferSize;

  private Path file;

  @Setup
  public void setUp() throws IOException {
    file = TestFile.ensureExists();
  }

  @Benchmark
  public long readLine() throws IOException {
    try (BufferedReader reader =
        new BufferedReader(new FileReader(file.toFile(), UTF_8), bufferSize)) {
      long sum = 0;
      String line;
      while ((line = reader.readLine()) != null) {
        sum += line.length();
      }
      return sum;
    }
  }
}
