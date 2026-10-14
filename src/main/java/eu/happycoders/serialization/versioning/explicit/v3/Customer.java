package eu.happycoders.serialization.versioning.explicit.v3;

import java.io.Serial;
import java.io.Serializable;

public class Customer implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;
  private final String emailAddress;

  public Customer(String name, String emailAddress) {
    this.name = name;
    this.emailAddress = emailAddress;
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", emailAddress=" + emailAddress + "]";
  }
}
