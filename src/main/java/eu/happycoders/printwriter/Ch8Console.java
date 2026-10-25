package eu.happycoders.printwriter;

import java.io.PrintWriter;

public class Ch8Console {

  static void main() {
    System.out.println("System.out encodes as " + System.out.charset());

    PrintWriter console = new PrintWriter(System.out, true);
    console.println("Grüße aus Köln");
    console.printf("%-5s %s%n", "WARN", "GET /java/bufferedreader/ 404");
  }
}
