package eu.happycoders.serialization.filter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

/** A comparator that shows when it is called. */
public class LoggingComparator implements Comparator<String>, Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @Override
  public int compare(String a, String b) {
    System.out.println("compare(" + a + ", " + b + ") called");
    return a.compareTo(b);
  }
}
