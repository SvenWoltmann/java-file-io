package eu.happycoders.serialization.custom.entity;

public class Entity {

  private long id;

  public Entity() {}

  public Entity(long id) {
    this.id = id;
  }

  public long id() {
    return id;
  }

  protected void setId(long id) {
    this.id = id;
  }
}
