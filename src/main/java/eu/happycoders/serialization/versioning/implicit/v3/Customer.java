package eu.happycoders.serialization.versioning.implicit.v3;

import java.io.Serializable;

public class Customer implements Serializable {

  private final String name;
  private final String email;

  public Customer(String name, String email) {
    assert email.contains("@");
    this.name = name;
    this.email = email;
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", email=" + email + "]";
  }
}
