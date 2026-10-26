package eu.happycoders.files.benchmark;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

/**
 * Copies a file of 100,000,000 bytes with {@code Files.copy()}, with and without {@code
 * COPY_ATTRIBUTES} (a clone on APFS since Java 20), with {@code InputStream.transferTo()}, with
 * {@code FileChannel.transferTo()}, and with a loop that copies through an 8,192-byte buffer from a
 * {@code FileInputStream} to a {@code FileOutputStream}. The teardown forces the copy to disk
 * before deleting it, so that write-back of earlier copies does not slow down the next one.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 2)
@Fork(3)
public class CopyBenchmark {

  private static final int SIZE = 100_000_000;
  private static final Path DIR = Path.of("target/copy-benchmark");

  private Path source;
  private Path target;

  @Setup(Level.Trial)
  public void createSource() throws IOException {
    Files.createDirectories(DIR);
    source = DIR.resolve("source.bin");
    byte[] bytes = new byte[SIZE];
    new Random(42).nextBytes(bytes);
    Files.write(source, bytes);
    target = DIR.resolve("target.bin");
  }

  @TearDown(Level.Invocation)
  public void deleteTarget() throws IOException {
    try (FileChannel channel = FileChannel.open(target, StandardOpenOption.WRITE)) {
      channel.force(true);
    }
    Files.delete(target);
  }

  @Benchmark
  public Path filesCopy() throws IOException {
    return Files.copy(source, target);
  }

  @Benchmark
  public Path filesCopyWithAttributes() throws IOException {
    return Files.copy(source, target, COPY_ATTRIBUTES);
  }

  @Benchmark
  public long inputStreamTransferTo() throws IOException {
    try (InputStream in = Files.newInputStream(source);
        OutputStream out = Files.newOutputStream(target)) {
      return in.transferTo(out);
    }
  }

  @Benchmark
  public long fileChannelTransferTo() throws IOException {
    try (FileChannel in = FileChannel.open(source);
        FileChannel out =
            FileChannel.open(target, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
      long position = 0;
      long size = in.size();
      while (position < size) {
        position += in.transferTo(position, size - position, out);
      }
      return position;
    }
  }

  @Benchmark
  public long bufferLoop() throws IOException {
    try (InputStream in = new FileInputStream(source.toFile());
        OutputStream out = new FileOutputStream(target.toFile())) {
      byte[] buffer = new byte[8192];
      long copied = 0;
      int read;
      while ((read = in.read(buffer)) != -1) {
        out.write(buffer, 0, read);
        copied += read;
      }
      return copied;
    }
  }
}
