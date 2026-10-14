package eu.happycoders.serialization;

import eu.happycoders.serialization.util.CountingInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Ch2Reading {

  static void main() throws IOException, ClassNotFoundException {
    Path file = Path.of("order.ser");
    try (var out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
      out.writeObject(Orders.sampleOrder());
    }

    System.out.println("== Pattern matching");
    try (var fileIn = new BufferedInputStream(Files.newInputStream(file));
        var in = new ObjectInputStream(fileIn)) {
      if (in.readObject() instanceof Order order) {
        System.out.println("Order " + order.id() + " of " + order.customer().name());
      }
    }

    System.out.println("== Several objects until EOF");
    Path ordersFile = Path.of("orders.ser");
    try (var out =
        new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(ordersFile)))) {
      for (long id = 1; id <= 3; id++) {
        out.writeObject(new Order(id, new Customer("Ada Lovelace", "ada@example.com")));
      }
    }
    try (var fileIn = new BufferedInputStream(Files.newInputStream(ordersFile));
        var in = new ObjectInputStream(fileIn)) {
      while (true) {
        Order order = (Order) in.readObject();
        System.out.println("Read order " + order.id());
      }
    } catch (EOFException e) {
      System.out.println("End of file");
    }

    System.out.println("== One list instead");
    Path listFile = Path.of("order-list.ser");
    List<Order> orders =
        List.of(
            new Order(1, new Customer("Ada Lovelace", "ada@example.com")),
            new Order(2, new Customer("Alan Turing", "alan@example.com")));
    try (var out =
        new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(listFile)))) {
      out.writeObject(orders);
    }
    try (var fileIn = new BufferedInputStream(Files.newInputStream(listFile));
        var in = new ObjectInputStream(fileIn)) {
      @SuppressWarnings("unchecked")
      List<Order> copy = (List<Order>) in.readObject();
      System.out.println("Read " + copy.size() + " orders");
    }

    System.out.println("== Calls to the stream below");
    byte[] data = serialize(Orders.sampleOrder());
    countReads(data, false);
    countReads(data, true);

    System.out.println("== Not a serialization stream");
    Path textFile = Path.of("hello.txt");
    Files.writeString(textFile, "Hello, World!");
    try (var fileIn = new BufferedInputStream(Files.newInputStream(textFile));
        var in = new ObjectInputStream(fileIn)) {
      System.out.println(in.readObject());
    } catch (StreamCorruptedException e) {
      System.out.println(e);
    }
  }

  private static byte[] serialize(Object object) throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (var out = new ObjectOutputStream(bytes)) {
      out.writeObject(object);
    }
    return bytes.toByteArray();
  }

  private static void countReads(byte[] data, boolean buffered)
      throws IOException, ClassNotFoundException {
    var counting = new CountingInputStream(new ByteArrayInputStream(data));
    InputStream source = buffered ? new BufferedInputStream(counting) : counting;
    try (var in = new ObjectInputStream(source)) {
      in.readObject();
    }
    System.out.println((buffered ? "Buffered: " : "Unbuffered: ") + counting.calls() + " calls");
  }
}
