package eu.happycoders.serialization;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  private final long id;
  private final Customer customer;
  private final List<OrderItem> items = new ArrayList<>();

  public Order(long id, Customer customer) {
    this.id = id;
    this.customer = customer;
  }

  public void addItem(OrderItem item) {
    items.add(item);
  }

  public long id() {
    return id;
  }

  public Customer customer() {
    return customer;
  }

  public List<OrderItem> items() {
    return List.copyOf(items);
  }

  @Override
  public String toString() {
    return "Order[id=" + id + ", customer=" + customer + ", items=" + items + "]";
  }
}
