package eu.happycoders.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * Writes a serializable lambda into {@code lambda.ser} or reads it back – the experiment from the
 * article on how the stream depends on the name javac gives the lambda's method:
 *
 * <ol>
 *   <li>{@code mvn compile}, then {@code java -cp target/classes
 *       eu.happycoders.serialization.Ch8LambdaNames write}
 *   <li>{@code java -cp target/classes eu.happycoders.serialization.Ch8LambdaNames read} prints the
 *       comparison – also after recompiling the unchanged class.
 *   <li>Rename the variable {@code byId} (e.g. to {@code orderById}), {@code mvn compile}, and run
 *       {@code read} again: the old stream no longer finds the lambda's method.
 * </ol>
 */
public class Ch8LambdaNames {

  static void main(String[] args) throws IOException, ClassNotFoundException {
    Path file = Path.of("lambda.ser");
    if (args.length > 0 && args[0].equals("write")) {
      Comparator<Order> byId =
          (Comparator<Order> & Serializable) (a, b) -> Long.compare(a.id(), b.id());
      try (var out =
          new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
        out.writeObject(byId);
      }
      System.out.println("Written to " + file);
      return;
    }

    try (var fileIn = new BufferedInputStream(Files.newInputStream(file));
        var in = new ObjectInputStream(fileIn)) {
      @SuppressWarnings("unchecked")
      Comparator<Order> comparator = (Comparator<Order>) in.readObject();
      Customer ada = new Customer("Ada Lovelace", "ada@example.com");
      System.out.println("compare: " + comparator.compare(new Order(1, ada), new Order(2, ada)));
    } catch (InvalidObjectException e) {
      System.out.println(e);
      System.out.println("Cause: " + e.getCause().getCause());
    }
  }
}
