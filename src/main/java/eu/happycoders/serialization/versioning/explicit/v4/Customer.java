package eu.happycoders.serialization.versioning.explicit.v4;

import java.io.Serial;
import java.io.Serializable;

public class Customer implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;
  private final EmailAddress email;

  public Customer(String name, EmailAddress email) {
    this.name = name;
    this.email = email;
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", email=" + email + "]";
  }
}
