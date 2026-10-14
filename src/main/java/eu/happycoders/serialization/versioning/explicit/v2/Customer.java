package eu.happycoders.serialization.versioning.explicit.v2;

import java.io.Serial;
import java.io.Serializable;

public class Customer implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;
  private final String email;
  private String phone;

  public Customer(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", email=" + email + ", phone=" + phone + "]";
  }
}
