package eu.happycoders.printwriter;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/** Counts how often a PrintStream and a PrintWriter, both with autoFlush, flush their stream. */
public class Ch9PrintStream {

  static void main() {
    FlushCounter streamCounter = new FlushCounter();
    FlushCounter writerCounter = new FlushCounter();
    PrintStream stream = new PrintStream(streamCounter, true, UTF_8);
    PrintWriter writer = new PrintWriter(writerCounter, true, UTF_8);

    stream.print("first line\nsecond line\n");
    writer.print("first line\nsecond line\n");
    System.out.println(
        "print(\"…\\n…\\n\"): PrintStream "
            + streamCounter.flushes
            + " flush(es), PrintWriter "
            + writerCounter.flushes);

    stream.print('x');
    writer.print('x');
    System.out.println(
        "print('x'):        PrintStream "
            + streamCounter.flushes
            + " flush(es), PrintWriter "
            + writerCounter.flushes);

    stream.println("third line");
    writer.println("third line");
    System.out.println(
        "println(…):        PrintStream "
            + streamCounter.flushes
            + " flush(es), PrintWriter "
            + writerCounter.flushes);

    stream.write(0x41);
    stream.write('\n');
    System.out.println("write('\\n'):       PrintStream " + streamCounter.flushes + " flush(es)");

    stream.close();
    writer.close();
  }

  /** An output stream that discards the bytes and counts the calls to flush(). */
  static final class FlushCounter extends FilterOutputStream {
    int flushes;

    FlushCounter() {
      super(OutputStream.nullOutputStream());
    }

    @Override
    public void flush() throws IOException {
      flushes++;
      super.flush();
    }
  }
}
