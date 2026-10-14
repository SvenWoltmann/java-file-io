package eu.happycoders.serialization.graph;

import java.io.Serial;
import java.io.Serializable;

public class Customer implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;
  private final Address address;

  public Customer(String name, Address address) {
    this.name = name;
    this.address = address;
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", address=" + address + "]";
  }
}
