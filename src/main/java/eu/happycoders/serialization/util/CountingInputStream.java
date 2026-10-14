package eu.happycoders.serialization.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/** Counts how often the stream below is called. */
public class CountingInputStream extends FilterInputStream {

  private long calls;

  public CountingInputStream(InputStream in) {
    super(in);
  }

  @Override
  public int read() throws IOException {
    calls++;
    return super.read();
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    calls++;
    return in.read(b, off, len);
  }

  public long calls() {
    return calls;
  }
}
