package eu.happycoders.serialization.custom;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;

public final class ResolvingStatus implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  public static final ResolvingStatus OPEN = new ResolvingStatus("OPEN");
  public static final ResolvingStatus SHIPPED = new ResolvingStatus("SHIPPED");

  private final String name;

  private ResolvingStatus(String name) {
    this.name = name;
  }

  @Serial
  private Object readResolve() throws ObjectStreamException {
    return switch (name) {
      case "OPEN" -> OPEN;
      case "SHIPPED" -> SHIPPED;
      default -> throw new InvalidObjectException("Unknown status: " + name);
    };
  }

  @Override
  public String toString() {
    return name;
  }
}
