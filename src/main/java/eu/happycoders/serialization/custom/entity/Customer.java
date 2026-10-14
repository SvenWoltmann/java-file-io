package eu.happycoders.serialization.custom.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

public class Customer extends Entity implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String name;

  public Customer(long id, String name) {
    super(id);
    this.name = name;
  }

  @Serial
  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeLong(id());
  }

  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    setId(in.readLong());
  }

  @Override
  public String toString() {
    return "Customer[id=" + id() + ", name=" + name + "]";
  }
}
