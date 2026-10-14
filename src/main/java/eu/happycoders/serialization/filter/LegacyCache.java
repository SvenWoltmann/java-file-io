package eu.happycoders.serialization.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/** Stands for library code that deserializes without setting a filter. */
public final class LegacyCache {

  private LegacyCache() {}

  public static Object load(byte[] data) throws IOException, ClassNotFoundException {
    try (var in = new ObjectInputStream(new ByteArrayInputStream(data))) {
      return in.readObject();
    }
  }
}
