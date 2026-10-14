package eu.happycoders.serialization;

import java.math.BigDecimal;

/** Creates the example orders used throughout the article. */
public final class Orders {

  private Orders() {}

  public static Order sampleOrder() {
    Customer customer = new Customer("Ada Lovelace", "ada@example.com");
    Order order = new Order(1001, customer);
    order.addItem(new OrderItem("Notebook", 2, new BigDecimal("4.99")));
    order.addItem(new OrderItem("Fountain pen", 1, new BigDecimal("39.90")));
    return order;
  }
}
