package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class Ch9Closing {

  static void main() throws IOException {
    try (BufferedReader reader = new BufferedReader(new ClosingReporter(Reader.of("Hello\n")))) {
      System.out.println(reader.readLine());
    }
    System.out.println("After the try block");
  }

  /** Wraps a reader and reports when it is closed. */
  private static class ClosingReporter extends FilterReader {
    ClosingReporter(Reader in) {
      super(in);
    }

    @Override
    public void close() throws IOException {
      System.out.println("Inner reader closed");
      super.close();
    }
  }
}
