package eu.happycoders.serialization;

import static eu.happycoders.serialization.util.Serialization.deserialize;
import static eu.happycoders.serialization.util.Serialization.serialize;

import java.io.IOException;
import java.io.InvalidClassException;

public class Ch6Inheritance {

  static void main() throws IOException, ClassNotFoundException {
    System.out.println("== Superclass with no-arg constructor");
    var customer = new eu.happycoders.serialization.inheritance.Customer(42, "Ada Lovelace");
    System.out.println("Before: " + customer);
    byte[] data = serialize(customer);
    System.out.println("-- reading");
    Object copy = deserialize(data);
    System.out.println("After:  " + copy);

    System.out.println("== Superclass without no-arg constructor");
    var other = new eu.happycoders.serialization.inheritance.nodefault.Customer(42, "Ada Lovelace");
    data = serialize(other);
    System.out.println(data.length + " bytes written");
    try {
      deserialize(data);
    } catch (InvalidClassException e) {
      System.out.println(e);
    }
  }
}
