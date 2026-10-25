package eu.happycoders.printwriter;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch5CheckError {

  static void main() throws IOException {
    System.out.println("== A writer that fails");
    PrintWriter out = new PrintWriter(new FailingWriter());
    out.println("2026-10-17 08:15:02 INFO  Server started on port 8080");
    System.out.println("println() returned normally");
    System.out.println("checkError(): " + out.checkError());
    out.close();

    System.out.println("== Writing after close()");
    PrintWriter closed = new PrintWriter(Writer.nullWriter());
    closed.close();
    closed.println("too late");
    System.out.println("checkError(): " + closed.checkError());

    System.out.println("== The euro sign in ISO-8859-1");
    Path first = Path.of("target/price-file-constructor.txt");
    try (PrintWriter latin1 = new PrintWriter(first.toFile(), ISO_8859_1)) {
      latin1.println("Preis: 5 €");
      System.out.println("new PrintWriter(File, ISO_8859_1), checkError(): " + latin1.checkError());
    }
    System.out.println("In the first file:  " + Files.readString(first, ISO_8859_1).strip());
    System.out.println();

    Path second = Path.of("target/price-new-buffered-writer.txt");
    try (PrintWriter latin1 = new PrintWriter(Files.newBufferedWriter(second, ISO_8859_1))) {
      latin1.println("Preis: 5 €");
      System.out.println(
          "Files.newBufferedWriter(), checkError():          " + latin1.checkError());
    }
    System.out.println("In the second file: \"" + Files.readString(second, ISO_8859_1) + "\"");
  }

  /** A writer that fails like a disk that is full or a connection that broke off. */
  static final class FailingWriter extends Writer {
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
      throw new IOException("Simulated write error");
    }

    @Override
    public void flush() throws IOException {
      throw new IOException("Simulated write error");
    }

    @Override
    public void close() {
      // nothing to close
    }
  }
}
