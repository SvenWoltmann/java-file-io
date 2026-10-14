package eu.happycoders.serialization.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Serializes objects into byte arrays and back, for the examples that need no file. */
public final class Serialization {

  private Serialization() {}

  public static byte[] serialize(Object object) throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (var out = new ObjectOutputStream(bytes)) {
      out.writeObject(object);
    }
    return bytes.toByteArray();
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(byte[] data) throws IOException, ClassNotFoundException {
    try (var in = new ObjectInputStream(new ByteArrayInputStream(data))) {
      return (T) in.readObject();
    }
  }

  public static <T> T copy(T object) throws IOException, ClassNotFoundException {
    return deserialize(serialize(object));
  }
}
