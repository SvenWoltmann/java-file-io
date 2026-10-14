package eu.happycoders.serialization.custom.proxy;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public final class OrderItem implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final String product;
  private final int quantity;
  private final BigDecimal price;

  public OrderItem(String product, int quantity, BigDecimal price) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive: " + quantity);
    }
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }

  @Serial
  private Object writeReplace() {
    return new SerializedForm(product, quantity, price);
  }

  @Serial
  private void readObject(ObjectInputStream in) throws InvalidObjectException {
    throw new InvalidObjectException("Serialization proxy required");
  }

  private record SerializedForm(String product, int quantity, BigDecimal price)
      implements Serializable {

    @Serial
    private Object readResolve() {
      return new OrderItem(product, quantity, price);
    }
  }

  @Override
  public String toString() {
    return "OrderItem[product=" + product + ", quantity=" + quantity + ", price=" + price + "]";
  }
}
