package eu.happycoders.serialization.transients;

import eu.happycoders.serialization.OrderItem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OrderWithReadObject implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final long id;
  private final List<OrderItem> items = new ArrayList<>();

  private transient BigDecimal total;
  private transient List<Consumer<OrderWithReadObject>> listeners = new ArrayList<>();

  public OrderWithReadObject(long id) {
    this.id = id;
  }

  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    listeners = new ArrayList<>();
  }

  public void addItem(OrderItem item) {
    items.add(item);
    total = null;
    for (Consumer<OrderWithReadObject> listener : listeners) {
      listener.accept(this);
    }
  }

  public void onChange(Consumer<OrderWithReadObject> listener) {
    listeners.add(listener);
  }

  public BigDecimal total() {
    if (total == null) {
      total =
          items.stream()
              .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
              .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    return total;
  }

  public long id() {
    return id;
  }
}
