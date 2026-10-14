package eu.happycoders.serialization.special;

import java.io.Serializable;
import java.math.BigDecimal;

public record OrderItem(String product, int quantity, BigDecimal price) implements Serializable {

  public OrderItem {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive: " + quantity);
    }
  }
}
