package eu.happycoders.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class Intro {

  static void main() throws IOException, ClassNotFoundException {
    Customer customer = new Customer("Ada Lovelace", "ada@example.com");
    Order order = new Order(1001, customer);
    order.addItem(new OrderItem("Notebook", 2, new BigDecimal("4.99")));
    order.addItem(new OrderItem("Fountain pen", 1, new BigDecimal("39.90")));

    Path file = Path.of("order.ser");

    try (var out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
      out.writeObject(order);
    }

    try (var fileIn = new BufferedInputStream(Files.newInputStream(file));
        var in = new ObjectInputStream(fileIn)) {
      Order copy = (Order) in.readObject();
      System.out.println(copy);
      System.out.println("Same object: " + (copy == order));
    }
  }
}
