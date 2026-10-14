package eu.happycoders.serialization.graph;

public class Address {

  private final String city;

  public Address(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return "Address[city=" + city + "]";
  }
}
