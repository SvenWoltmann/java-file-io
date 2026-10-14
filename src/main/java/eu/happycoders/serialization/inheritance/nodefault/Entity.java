package eu.happycoders.serialization.inheritance.nodefault;

public class Entity {

  private final long id;

  public Entity(long id) {
    this.id = id;
  }

  public long id() {
    return id;
  }
}
