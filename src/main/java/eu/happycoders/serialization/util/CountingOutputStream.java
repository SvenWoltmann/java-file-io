package eu.happycoders.serialization.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/** Counts how often the stream below is called. */
public class CountingOutputStream extends FilterOutputStream {

  private long calls;

  public CountingOutputStream(OutputStream out) {
    super(out);
  }

  @Override
  public void write(int b) throws IOException {
    calls++;
    out.write(b);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    calls++;
    out.write(b, off, len);
  }

  public long calls() {
    return calls;
  }
}
