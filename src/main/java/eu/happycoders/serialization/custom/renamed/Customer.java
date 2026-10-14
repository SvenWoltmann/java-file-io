package eu.happycoders.serialization.custom.renamed;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

/** The version of {@code Customer} in which {@code email} became {@code emailAddress}. */
public class Customer implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private String name;
  private String emailAddress;

  public Customer(String name, String emailAddress) {
    this.name = name;
    this.emailAddress = emailAddress;
  }

  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    ObjectInputStream.GetField fields = in.readFields();
    name = (String) fields.get("name", null);
    if (fields.defaulted("emailAddress")) {
      emailAddress = (String) fields.get("email", null); // written by the old version
    } else {
      emailAddress = (String) fields.get("emailAddress", null);
    }
  }

  @Override
  public String toString() {
    return "Customer[name=" + name + ", emailAddress=" + emailAddress + "]";
  }
}
