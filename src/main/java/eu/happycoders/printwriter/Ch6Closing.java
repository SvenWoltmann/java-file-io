package eu.happycoders.printwriter;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class Ch6Closing {

  static void main() {
    System.out.println("== Closing the PrintWriter closes the writer inside it");
    try (PrintWriter out = new PrintWriter(new ClosingReporter(Writer.nullWriter()))) {
      out.println("2026-10-17 08:15:02 INFO  Server started on port 8080");
    }
    System.out.println("After the try block");

    System.out.println("== Closing a PrintWriter around System.out");
    PrintWriter console = new PrintWriter(System.out, true);
    console.println("Via the PrintWriter");
    console.close();
    System.out.println("Via System.out after close()");
    System.err.println("System.out.checkError(): " + System.out.checkError());
  }

  /** A writer that reports when it is closed and then closes the writer it wraps. */
  static final class ClosingReporter extends FilterWriter {
    ClosingReporter(Writer out) {
      super(out);
    }

    @Override
    public void close() throws IOException {
      System.out.println("Inner writer closed");
      super.close();
    }
  }
}
