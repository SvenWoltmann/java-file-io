package eu.happycoders.serialization;

import static eu.happycoders.serialization.util.Serialization.serialize;

import eu.happycoders.serialization.filter.DeserializationBomb;
import eu.happycoders.serialization.filter.PriorityQueues;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.util.Locale;

public class Ch10Filters {

  private static final String ORDER_CLASSES =
      "eu.happycoders.serialization.*;java.util.ArrayList;java.math.BigDecimal;"
          + "java.math.BigInteger;java.lang.Number;java.lang.Object";

  static void main() throws IOException, ClassNotFoundException {
    System.out.println("== Writing the queue");
    byte[] queueData = PriorityQueues.serializedQueue();
    byte[] orderData = serialize(Orders.sampleOrder());

    System.out.println("== Without filter");
    read(queueData, null);

    System.out.println("== Allow-list for orders: an order");
    read(orderData, ORDER_CLASSES + ";!*");

    System.out.println("== Allow-list for orders: a priority queue");
    read(queueData, ORDER_CLASSES + ";!*");

    System.out.println("== Allow-list without !*: a priority queue");
    read(queueData, ORDER_CLASSES);

    System.out.println("== Limit: maxdepth");
    byte[] bombData = serialize(DeserializationBomb.create());
    System.out.println(bombData.length + " bytes");
    long start = System.nanoTime();
    read(bombData, "maxdepth=20");
    System.out.printf(Locale.ROOT, "Rejected after %.1f ms%n", (System.nanoTime() - start) / 1e6);

    System.out.println("== Limit: maxarray");
    read(serialize(new byte[1_000_000]), "maxarray=100000");
  }

  private static void read(byte[] data, String pattern) throws IOException, ClassNotFoundException {
    try (var in = new ObjectInputStream(new ByteArrayInputStream(data))) {
      if (pattern != null) {
        in.setObjectInputFilter(ObjectInputFilter.Config.createFilter(pattern));
      }
      Object object = in.readObject();
      System.out.println("Read: " + object.getClass().getSimpleName());
    } catch (InvalidClassException e) {
      System.out.println(e);
    }
  }
}
