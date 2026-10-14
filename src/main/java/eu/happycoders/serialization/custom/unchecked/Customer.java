package eu.happycoders.serialization.custom.unchecked;

import java.io.Serial;
import java.io.Serializable;

/** The same serialized form as {@code Customer}, but without validation in the constructor. */
public class Customer implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;
  private final String email;

  public Customer(String name, String email) {
    this.name = name;
    this.email = email;
  }
}
