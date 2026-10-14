package eu.happycoders.serialization.versioning.implicit.v2;

import java.io.Serializable;

public class Customer implements Serializable {

  private final String name;
  private final String email;

  public Customer(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String name() {
    return name;
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", email=" + email + "]";
  }
}
