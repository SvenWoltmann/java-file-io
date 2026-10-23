package eu.happycoders.printwriter.benchmark;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedWriter;
import java.io.FilterOutputStream;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * Not a JMH benchmark: writes the lines of the test text with {@code println()} through three
 * chains and counts, for each, the write calls that arrive at the {@code OutputStreamWriter} and
 * the write calls that arrive at the {@code OutputStream} below it. The stream discards the bytes:
 * the counts do not depend on where the bytes go.
 *
 * <p>The {@code OutputStreamWriter} stands for the {@code FileWriter}, which is an {@code
 * OutputStreamWriter} around a {@code FileOutputStream}.
 *
 * <pre>
 * java -cp target/benchmarks.jar eu.happycoders.printwriter.benchmark.WriteCallCounter
 * </pre>
 */
public final class WriteCallCounter {

  private WriteCallCounter() {}

  static void main() {
    String[] lines = TestText.lines();
    System.out.println(lines.length + " lines");
    count("PrintWriter -> OutputStreamWriter", lines, false, false);
    count("PrintWriter -> BufferedWriter -> OutputStreamWriter", lines, true, false);
    count("PrintWriter(autoFlush) -> BufferedWriter -> OutputStreamWriter", lines, true, true);
  }

  private static void count(String chain, String[] lines, boolean buffered, boolean autoFlush) {
    CountingStream stream = new CountingStream();
    CountingWriter encoder = new CountingWriter(new OutputStreamWriter(stream, UTF_8));
    Writer below = buffered ? new BufferedWriter(encoder) : encoder;
    try (PrintWriter out = new PrintWriter(below, autoFlush)) {
      for (String line : lines) {
        out.println(line);
      }
    }
    System.out.printf(
        Locale.ROOT,
        "%s:%n  %,d calls to the OutputStreamWriter, %,d writes to the OutputStream%n",
        chain,
        encoder.calls,
        stream.writes);
  }

  private static final class CountingWriter extends FilterWriter {
    long calls;

    CountingWriter(Writer out) {
      super(out);
    }

    @Override
    public void write(int c) throws IOException {
      calls++;
      super.write(c);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
      calls++;
      super.write(cbuf, off, len);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
      calls++;
      super.write(str, off, len);
    }
  }

  private static final class CountingStream extends FilterOutputStream {
    long writes;

    CountingStream() {
      super(OutputStream.nullOutputStream());
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      writes++;
      out.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
      writes++;
      out.write(b);
    }
  }
}
