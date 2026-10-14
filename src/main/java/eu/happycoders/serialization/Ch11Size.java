package eu.happycoders.serialization;

import static eu.happycoders.serialization.util.Serialization.serialize;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Ch11Size {

  static void main() throws IOException {
    System.out.println("== One order");
    Order order = Orders.sampleOrder();
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (var out = new DataOutputStream(bytes)) {
      writeOrder(order, out);
    }
    System.out.println("ObjectOutputStream: " + serialize(order).length + " bytes");
    System.out.println("DataOutputStream:   " + bytes.size() + " bytes");

    System.out.println("== 1,000 orders of 1,000 customers");
    List<Order> orders = new ArrayList<>();
    for (long id = 1; id <= 1_000; id++) {
      Order next = new Order(id, new Customer("Customer " + id, "customer" + id + "@example.com"));
      next.addItem(new OrderItem("Notebook", 2, new BigDecimal("4.99")));
      next.addItem(new OrderItem("Fountain pen", 1, new BigDecimal("39.90")));
      orders.add(next);
    }
    bytes = new ByteArrayOutputStream();
    try (var out = new DataOutputStream(bytes)) {
      out.writeInt(orders.size());
      for (Order next : orders) {
        writeOrder(next, out);
      }
    }
    System.out.println("ObjectOutputStream: " + serialize(orders).length + " bytes");
    System.out.println("DataOutputStream:   " + bytes.size() + " bytes");
  }

  private static void writeOrder(Order order, DataOutputStream out) throws IOException {
    out.writeLong(order.id());
    out.writeUTF(order.customer().name());
    out.writeUTF(order.customer().email());
    out.writeInt(order.items().size());
    for (OrderItem item : order.items()) {
      out.writeUTF(item.product());
      out.writeInt(item.quantity());
      out.writeUTF(item.price().toString());
    }
  }
}
