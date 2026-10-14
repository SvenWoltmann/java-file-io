package eu.happycoders.serialization.filter;

import java.util.HashSet;
import java.util.Set;

/**
 * Builds the object graph of Joshua Bloch's deserialization bomb (Effective Java, 3rd edition, item
 * 85): 100 levels of nested sets whose deserialization does not finish in practice, because every
 * set computes the hash codes of its elements while it is read.
 */
public final class DeserializationBomb {

  private DeserializationBomb() {}

  public static Set<Object> create() {
    Set<Object> root = new HashSet<>();
    Set<Object> s1 = root;
    Set<Object> s2 = new HashSet<>();
    for (int i = 0; i < 100; i++) {
      Set<Object> t1 = new HashSet<>();
      Set<Object> t2 = new HashSet<>();
      t1.add("foo"); // makes t1 unequal to t2
      s1.add(t1);
      s1.add(t2);
      s2.add(t1);
      s2.add(t2);
      s1 = t1;
      s2 = t2;
    }
    return root;
  }
}
