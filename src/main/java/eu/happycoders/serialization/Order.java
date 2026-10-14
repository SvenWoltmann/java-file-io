package eu.happycoders.serialization;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
  public boolean equals(Object o) {
    return o instanceof Order other
        && id == other.id
        && customer.equals(other.customer)
        && items.equals(other.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, customer, items);
  }

  @Override
  public String toString() {
    return "Order[id=" + id + ", customer=" + customer + ", items=" + items + "]";
  }
}
