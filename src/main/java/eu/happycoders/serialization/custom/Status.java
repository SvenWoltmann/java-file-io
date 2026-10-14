package eu.happycoders.serialization.custom;

import java.io.Serial;
import java.io.Serializable;

public final class Status implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  public static final Status OPEN = new Status("OPEN");
  public static final Status SHIPPED = new Status("SHIPPED");

  private final String name;

  private Status(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
