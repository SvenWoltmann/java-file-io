package eu.happycoders.serialization;

import eu.happycoders.serialization.filter.LegacyCache;
import eu.happycoders.serialization.filter.PriorityQueues;
import java.io.IOException;
import java.io.InvalidClassException;

/**
 * Reads a priority queue without a stream-specific filter. Run it with and without a JVM-wide
 * filter:
 *
 * <pre>
 * java -Djdk.serialFilter='java.base/*;!*' \
 *     -cp target/classes eu.happycoders.serialization.Ch10GlobalFilter
 * </pre>
 */
public class Ch10GlobalFilter {

  static void main() throws IOException, ClassNotFoundException {
    System.out.println("== Writing the queue");
    byte[] queueData = PriorityQueues.serializedQueue();

    System.out.println("== Reading the queue");
    try {
      Object object = LegacyCache.load(queueData);
      System.out.println("Read: " + object.getClass().getSimpleName());
    } catch (InvalidClassException e) {
      System.out.println(e);
    }
  }
}
