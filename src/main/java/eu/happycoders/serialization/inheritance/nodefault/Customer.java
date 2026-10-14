package eu.happycoders.serialization.inheritance.nodefault;

import java.io.Serial;
import java.io.Serializable;

public class Customer extends Entity implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;

  public Customer(long id, String name) {
    super(id);
    this.name = name;
  }

  @Override
  public String toString() {
    return "Customer[id=" + id() + ", name=" + name + "]";
  }
}
