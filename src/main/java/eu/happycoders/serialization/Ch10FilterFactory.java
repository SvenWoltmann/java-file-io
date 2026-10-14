package eu.happycoders.serialization;

import eu.happycoders.serialization.filter.LegacyCache;
import eu.happycoders.serialization.filter.PriorityQueues;
import java.io.InvalidClassException;
import java.io.ObjectInputFilter;

public class Ch10FilterFactory {

  private static final ScopedValue<ObjectInputFilter> ALLOWED = ScopedValue.newInstance();

  private static final ObjectInputFilter ORDERS_ONLY =
      ObjectInputFilter.Config.createFilter(
          "eu.happycoders.serialization.*;java.util.ArrayList;java.math.BigDecimal;"
              + "java.math.BigInteger;java.lang.Number;java.lang.Object;!*");

  static void main() throws Exception {
    ObjectInputFilter.Config.setSerialFilterFactory(Ch10FilterFactory::chooseFilter);

    System.out.println("== Writing the queue");
    byte[] queueData = PriorityQueues.serializedQueue();

    System.out.println("== Library call outside the scope");
    loadAndPrint(queueData);

    System.out.println("== Library call inside the scope");
    ScopedValue.where(ALLOWED, ORDERS_ONLY)
        .call(
            () -> {
              loadAndPrint(queueData);
              return null;
            });
  }

  private static ObjectInputFilter chooseFilter(ObjectInputFilter current, ObjectInputFilter next) {
    return ALLOWED.isBound() ? ObjectInputFilter.merge(ALLOWED.get(), next) : next;
  }

  private static void loadAndPrint(byte[] data) throws Exception {
    try {
      Object object = LegacyCache.load(data);
      System.out.println("Read: " + object.getClass().getSimpleName());
    } catch (InvalidClassException e) {
      System.out.println(e);
    }
  }
}
