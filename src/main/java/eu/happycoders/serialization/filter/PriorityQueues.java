package eu.happycoders.serialization.filter;

import static eu.happycoders.serialization.util.Serialization.serialize;

import java.io.IOException;
import java.util.PriorityQueue;

/** Serializes a priority queue whose comparator prints each call. */
public final class PriorityQueues {

  private PriorityQueues() {}

  public static byte[] serializedQueue() throws IOException {
    PriorityQueue<String> queue = new PriorityQueue<>(new LoggingComparator());
    queue.add("Notebook");
    queue.add("Fountain pen");
    queue.add("Ink");
    return serialize(queue);
  }
}
