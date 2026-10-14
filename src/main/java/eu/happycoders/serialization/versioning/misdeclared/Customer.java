package eu.happycoders.serialization.versioning.misdeclared;

import java.io.Serializable;

public class Customer implements Serializable {

  // wrong type: must be long, so serialization ignores this field
  private static final int serialVersionUID = 1;

  private final String name;
  private final String email;

  public Customer(String name, String email) {
    this.name = name;
    this.email = email;
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", email=" + email + "]";
  }
}
