package eu.happycoders.serialization;

import static eu.happycoders.serialization.util.Serialization.copy;
import static eu.happycoders.serialization.util.Serialization.serialize;

import eu.happycoders.serialization.special.OrderService;
import eu.happycoders.serialization.special.OrderStatus;
import eu.happycoders.serialization.util.PackageSwitchingObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.NotSerializableException;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Ch8Special {

  static void main() throws IOException, ClassNotFoundException {
    System.out.println("== Record: invalid data");
    var invalid =
        new eu.happycoders.serialization.special.unchecked.OrderItem(
            "Notebook", 0, new BigDecimal("4.99"));
    try (var in =
        new PackageSwitchingObjectInputStream(
            new ByteArrayInputStream(serialize(invalid)),
            eu.happycoders.serialization.special.unchecked.OrderItem.class,
            eu.happycoders.serialization.special.OrderItem.class)) {
      System.out.println(in.readObject());
    } catch (InvalidObjectException e) {
      System.out.println(e);
      System.out.println("Cause: " + e.getCause());
    }

    System.out.println("== Record: serialVersionUID");
    System.out.println(ObjectStreamClass.lookup(OrderItem.class).getSerialVersionUID());

    System.out.println("== Enum");
    OrderStatus status = copy(OrderStatus.OPEN);
    System.out.println(status + " == OrderStatus.OPEN: " + (status == OrderStatus.OPEN));
    System.out.println(serialize(OrderStatus.OPEN).length + " bytes");

    System.out.println("== Enum: constant renamed");
    try (var in =
        new PackageSwitchingObjectInputStream(
            new ByteArrayInputStream(serialize(OrderStatus.SHIPPED)),
            OrderStatus.class,
            eu.happycoders.serialization.special.renamed.OrderStatus.class)) {
      System.out.println(in.readObject());
    } catch (InvalidObjectException e) {
      System.out.println(e);
    }

    System.out.println("== Lambda");
    Comparator<Order> byId = (a, b) -> Long.compare(a.id(), b.id());
    try {
      serialize(byId);
    } catch (NotSerializableException e) {
      System.out.println(e);
    }

    System.out.println("== Lambda with intersection cast");
    Comparator<Order> serializableById =
        (Comparator<Order> & Serializable) (a, b) -> Long.compare(a.id(), b.id());
    byte[] lambdaData = serialize(serializableById);
    System.out.println(lambdaData.length + " bytes");
    System.out.println(
        "Contains SerializedLambda: "
            + new String(lambdaData, StandardCharsets.ISO_8859_1)
                .contains("java.lang.invoke.SerializedLambda"));
    Pattern.compile("lambda\\$main\\$[0-9a-f]+\\$\\d+")
        .matcher(new String(lambdaData, StandardCharsets.ISO_8859_1))
        .results()
        .forEach(match -> System.out.println("Method: " + match.group()));

    System.out.println("== TreeSet with Comparator.comparing()");
    TreeSet<Order> orders = new TreeSet<>(Comparator.comparing(Order::id));
    orders.add(Orders.sampleOrder());
    try {
      serialize(orders);
    } catch (NotSerializableException e) {
      System.out.println(e);
    }

    System.out.println("== TreeSet with a serializable key extractor");
    TreeSet<Order> serializableOrders =
        new TreeSet<>(Comparator.comparing((Function<Order, Long> & Serializable) Order::id));
    serializableOrders.add(Orders.sampleOrder());
    TreeSet<Order> ordersCopy = copy(serializableOrders);
    System.out.println(ordersCopy.size() + " order");

    System.out.println("== Inner class");
    OrderService service = new OrderService();
    OrderService.Draft draft = service.newDraft("Ada Lovelace");
    try {
      serialize(draft);
    } catch (NotSerializableException e) {
      System.out.println(e);
    }
  }
}
