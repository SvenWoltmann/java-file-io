package eu.happycoders.serialization;

import eu.happycoders.serialization.graph.Address;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class Ch3ObjectGraph {

  static void main() throws IOException, ClassNotFoundException {
    System.out.println("== Field of a non-serializable type");
    var customer =
        new eu.happycoders.serialization.graph.Customer("Ada Lovelace", new Address("London"));
    try (var out = new ObjectOutputStream(new ByteArrayOutputStream())) {
      out.writeObject(customer);
    } catch (NotSerializableException e) {
      System.out.println(e);
    }

    System.out.println("== Shared reference in one writeObject() call");
    Customer ada = new Customer("Ada Lovelace", "ada@example.com");
    List<Order> orders = List.of(new Order(1, ada), new Order(2, ada));
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (var out = new ObjectOutputStream(bytes)) {
      out.writeObject(orders);
    }
    try (var in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
      @SuppressWarnings("unchecked")
      List<Order> copy = (List<Order>) in.readObject();
      System.out.println("Same customer: " + (copy.get(0).customer() == copy.get(1).customer()));
    }

    System.out.println("== Shared reference in two writeObject() calls");
    bytes = new ByteArrayOutputStream();
    try (var out = new ObjectOutputStream(bytes)) {
      out.writeObject(new Order(1, ada));
      out.writeObject(new Order(2, ada));
    }
    try (var in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
      Order first = (Order) in.readObject();
      Order second = (Order) in.readObject();
      System.out.println("Same customer: " + (first.customer() == second.customer()));
    }

    System.out.println("== Same object written twice");
    Order order = new Order(1001, ada);
    order.addItem(new OrderItem("Notebook", 2, new BigDecimal("4.99")));
    bytes = new ByteArrayOutputStream();
    try (var out = new ObjectOutputStream(bytes)) {
      out.writeObject(order);
      order.addItem(new OrderItem("Fountain pen", 1, new BigDecimal("39.90")));
      out.writeObject(order);
    }
    readTwoOrders(bytes.toByteArray());

    System.out.println("== Same object written twice, with reset()");
    order = new Order(1001, ada);
    order.addItem(new OrderItem("Notebook", 2, new BigDecimal("4.99")));
    bytes = new ByteArrayOutputStream();
    try (var out = new ObjectOutputStream(bytes)) {
      out.writeObject(order);
      order.addItem(new OrderItem("Fountain pen", 1, new BigDecimal("39.90")));
      out.reset();
      out.writeObject(order);
    }
    readTwoOrders(bytes.toByteArray());

    System.out.println("== Same object written twice, with writeUnshared()");
    order = new Order(1001, ada);
    order.addItem(new OrderItem("Notebook", 2, new BigDecimal("4.99")));
    bytes = new ByteArrayOutputStream();
    try (var out = new ObjectOutputStream(bytes)) {
      out.writeObject(order);
      order.addItem(new OrderItem("Fountain pen", 1, new BigDecimal("39.90")));
      out.writeUnshared(order);
    }
    readTwoOrders(bytes.toByteArray());
  }

  private static void readTwoOrders(byte[] data) throws IOException, ClassNotFoundException {
    try (var in = new ObjectInputStream(new ByteArrayInputStream(data))) {
      Order first = (Order) in.readObject();
      Order second = (Order) in.readObject();
      System.out.println("First:  " + first.items().size() + " items");
      System.out.println("Second: " + second.items().size() + " items");
      System.out.println("Same object: " + (first == second));
    }
  }
}
