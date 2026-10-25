package eu.happycoders.bufferedreader;

/** Reads one line with {@code IO.readln()} (Java 25). Run it in a terminal. */
public class Ch7Readln {

  static void main() {
    String name = IO.readln("Your name: ");
    IO.println("Hello, " + name + "!");
  }
}
