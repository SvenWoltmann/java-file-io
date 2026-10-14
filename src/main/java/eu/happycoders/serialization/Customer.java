package eu.happycoders.serialization;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;
  private final String email;

  public Customer(String name, String email) {
    if (!email.contains("@")) {
      throw new IllegalArgumentException("Invalid email: " + email);
    }
    this.name = name;
    this.email = email;
  }

  public String name() {
    return name;
  }

  public String email() {
    return email;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Customer other && name.equals(other.name) && email.equals(other.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, email);
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", email=" + email + "]";
  }
}
