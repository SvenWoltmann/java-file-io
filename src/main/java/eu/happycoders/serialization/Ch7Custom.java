package eu.happycoders.serialization;

import static eu.happycoders.serialization.util.Serialization.copy;
import static eu.happycoders.serialization.util.Serialization.serialize;

import eu.happycoders.serialization.custom.ResolvingStatus;
import eu.happycoders.serialization.custom.Status;
import eu.happycoders.serialization.custom.proxy.OrderItem;
import eu.happycoders.serialization.util.PackageSwitchingObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class Ch7Custom {

  static void main() throws IOException, ClassNotFoundException {
    var invalid = new eu.happycoders.serialization.custom.unchecked.Customer("Ada", "no email");
    byte[] data = serialize(invalid);

    System.out.println("== Invalid data, read without readObject()");
    try (var in =
        new PackageSwitchingObjectInputStream(
            new ByteArrayInputStream(data),
            eu.happycoders.serialization.custom.unchecked.Customer.class,
            Customer.class)) {
      System.out.println(in.readObject());
    }

    System.out.println("== Invalid data, read with readObject()");
    try (var in =
        new PackageSwitchingObjectInputStream(
            new ByteArrayInputStream(data),
            eu.happycoders.serialization.custom.unchecked.Customer.class,
            eu.happycoders.serialization.custom.validating.Customer.class)) {
      System.out.println(in.readObject());
    } catch (InvalidObjectException e) {
      System.out.println(e);
    }

    System.out.println("== writeObject() and readObject() for the superclass state");
    var customer = new eu.happycoders.serialization.custom.entity.Customer(42, "Ada Lovelace");
    System.out.println(copy(customer));

    System.out.println("== Singleton without readResolve()");
    Status status = copy(Status.OPEN);
    System.out.println(status + " == Status.OPEN: " + (status == Status.OPEN));

    System.out.println("== Singleton with readResolve()");
    ResolvingStatus resolved = copy(ResolvingStatus.OPEN);
    System.out.println(
        resolved + " == ResolvingStatus.OPEN: " + (resolved == ResolvingStatus.OPEN));

    System.out.println("== Serialization proxy");
    OrderItem item = new OrderItem("Notebook", 2, new BigDecimal("4.99"));
    byte[] proxyData = serialize(item);
    String text = new String(proxyData, StandardCharsets.ISO_8859_1);
    System.out.println(
        "Stream contains OrderItem$SerializedForm: " + text.contains("OrderItem$SerializedForm"));
    Object proxyCopy = copy(item);
    System.out.println(proxyCopy.getClass().getSimpleName() + ": " + proxyCopy);
  }
}
