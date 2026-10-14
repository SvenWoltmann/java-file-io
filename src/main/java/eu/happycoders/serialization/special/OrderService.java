package eu.happycoders.serialization.special;

import java.io.Serial;
import java.io.Serializable;

public class OrderService {

  public Draft newDraft(String customerName) {
    return new Draft(customerName);
  }

  public class Draft implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    private final String customerName;

    Draft(String customerName) {
      this.customerName = customerName;
    }

    @Override
    public String toString() {
      return "Draft[customerName=" + customerName + "]";
    }
  }
}
