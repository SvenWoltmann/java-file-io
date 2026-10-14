package eu.happycoders.serialization;

import eu.happycoders.serialization.util.CountingOutputStream;
import eu.happycoders.serialization.util.HexDump;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Ch1Writing {

  static void main() throws IOException {
    Order order = Orders.sampleOrder();

    System.out.println("== Into a byte array");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (var out = new ObjectOutputStream(bytes)) {
      out.writeObject(order);
    }
    byte[] data = bytes.toByteArray();
    System.out.println(data.length + " bytes");
    HexDump.print(data, 96);

    System.out.println("== Calls to the stream below");
    var counting = new CountingOutputStream(OutputStream.nullOutputStream());
    try (var out = new ObjectOutputStream(counting)) {
      out.writeObject(order);
    }
    System.out.println("Unbuffered: " + counting.calls() + " calls");

    counting = new CountingOutputStream(OutputStream.nullOutputStream());
    try (var out = new ObjectOutputStream(new BufferedOutputStream(counting))) {
      out.writeObject(order);
    }
    System.out.println("Buffered: " + counting.calls() + " calls");
  }
}
