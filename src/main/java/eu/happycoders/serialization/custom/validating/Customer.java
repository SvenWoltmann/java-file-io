package eu.happycoders.serialization.custom.validating;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

public class Customer implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;
  private final String email;

  public Customer(String name, String email) {
    if (!isValidEmail(email)) {
      throw new IllegalArgumentException("Invalid email: " + email);
    }
    this.name = name;
    this.email = email;
  }

  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    if (!isValidEmail(email)) {
      throw new InvalidObjectException("Invalid email: " + email);
    }
  }

  private static boolean isValidEmail(String email) {
    return email != null && email.contains("@");
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", email=" + email + "]";
  }
}
