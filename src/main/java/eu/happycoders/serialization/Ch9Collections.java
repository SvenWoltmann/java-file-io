package eu.happycoders.serialization;

import static eu.happycoders.serialization.util.Serialization.copy;
import static eu.happycoders.serialization.util.Serialization.serialize;

import java.io.IOException;
import java.io.NotSerializableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ch9Collections {

  static void main() throws IOException, ClassNotFoundException {
    System.out.println("== ArrayList: capacity");
    List<String> small = new ArrayList<>();
    List<String> large = new ArrayList<>(10_000);
    for (String product : List.of("Notebook", "Fountain pen", "Ink")) {
      small.add(product);
      large.add(product);
    }
    System.out.println("Capacity 10:     " + serialize(small).length + " bytes");
    System.out.println("Capacity 10,000: " + serialize(large).length + " bytes");

    System.out.println("== Unmodifiable list");
    List<String> products = List.of("Notebook", "Fountain pen", "Ink");
    List<String> productsCopy = copy(products);
    System.out.println(productsCopy.getClass().getName());
    try {
      productsCopy.add("Eraser");
    } catch (UnsupportedOperationException e) {
      System.out.println(e);
    }

    System.out.println("== Views");
    Map<String, Integer> stock = new HashMap<>(Map.of("Notebook", 120, "Ink", 35));
    try {
      serialize(stock.keySet());
    } catch (NotSerializableException e) {
      System.out.println(e);
    }
    try {
      serialize(small.subList(0, 2));
    } catch (NotSerializableException e) {
      System.out.println(e);
    }
    System.out.println(copy(new ArrayList<>(stock.keySet())));
  }
}
