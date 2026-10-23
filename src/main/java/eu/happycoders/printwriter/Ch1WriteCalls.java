package eu.happycoders.printwriter;

import java.io.BufferedWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Counts the write calls that arrive at the writer below a {@code PrintWriter} when it writes the
 * five log lines with {@code printf()}: once directly, once with a {@code BufferedWriter} in
 * between.
 */
public class Ch1WriteCalls {

  static void main() {
    CountingWriter direct = new CountingWriter(Writer.nullWriter());
    try (PrintWriter out = new PrintWriter(direct)) {
      writeLog(out);
    }
    System.out.println("Without BufferedWriter: " + direct.calls + " write calls");

    CountingWriter buffered = new CountingWriter(Writer.nullWriter());
    try (PrintWriter out = new PrintWriter(new BufferedWriter(buffered))) {
      writeLog(out);
    }
    System.out.println("With BufferedWriter:    " + buffered.calls + " write call(s)");
  }

  private static void writeLog(PrintWriter out) {
    for (LogEntry entry : LogEntry.SERVER_LOG) {
      out.printf("%s %-5s %s%n", entry.timestamp(), entry.level(), entry.message());
    }
  }

  /** A writer that counts how often one of its write methods is called. */
  static final class CountingWriter extends FilterWriter {
    int calls;

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
}
