package eu.happycoders.serialization.inheritance;

import java.io.Serial;
import java.io.Serializable;

public class Customer extends Entity implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;

  public Customer(long id, String name) {
    super(id);
    this.name = name;
    System.out.println("Customer(id, name) called");
  }

  @Override
  public String toString() {
    return "Customer[id=" + id() + ", name=" + name + "]";
  }
}
