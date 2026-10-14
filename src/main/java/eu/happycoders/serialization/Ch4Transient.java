package eu.happycoders.serialization;

import static eu.happycoders.serialization.util.Serialization.copy;

import eu.happycoders.serialization.transients.Order;
import eu.happycoders.serialization.transients.OrderWithReadObject;
import java.io.IOException;
import java.math.BigDecimal;

public class Ch4Transient {

  static void main() throws IOException, ClassNotFoundException {
    System.out.println("== Before writing");
    Order order = new Order(1001);
    order.onChange(o -> System.out.println("Order " + o.id() + " changed"));
    order.addItem(new OrderItem("Notebook", 2, new BigDecimal("4.99")));
    System.out.println("Total: " + order.total());

    System.out.println("== After reading");
    Order copy = copy(order);
    System.out.println("Total: " + copy.total());
    try {
      copy.addItem(new OrderItem("Fountain pen", 1, new BigDecimal("39.90")));
    } catch (NullPointerException e) {
      System.out.println(e);
    }

    System.out.println("== After reading, with readObject()");
    OrderWithReadObject fixed = new OrderWithReadObject(1001);
    fixed.addItem(new OrderItem("Notebook", 2, new BigDecimal("4.99")));
    OrderWithReadObject fixedCopy = copy(fixed);
    fixedCopy.onChange(o -> System.out.println("Order " + o.id() + " changed"));
    fixedCopy.addItem(new OrderItem("Fountain pen", 1, new BigDecimal("39.90")));
    System.out.println("Total: " + fixedCopy.total());
  }
}
