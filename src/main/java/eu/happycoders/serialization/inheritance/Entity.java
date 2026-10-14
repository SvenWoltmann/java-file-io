package eu.happycoders.serialization.inheritance;

public class Entity {

  private long id;

  public Entity() {
    System.out.println("Entity() called");
  }

  public Entity(long id) {
    this.id = id;
  }

  public long id() {
    return id;
  }
}
