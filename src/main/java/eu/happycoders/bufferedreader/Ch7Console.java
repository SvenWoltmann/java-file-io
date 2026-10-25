package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Reads lines from the console until an empty line or the end of the input (Ctrl+D, on Windows
 * Ctrl+Z). Run it in a terminal, or pipe a file into it: {@code java -cp target/classes
 * eu.happycoders.bufferedreader.Ch7Console < files/server.log}
 */
public class Ch7Console {

  static void main() throws IOException {
    // No try-with-resources: closing the reader would close System.in.
    BufferedReader in =
        new BufferedReader(new InputStreamReader(System.in, System.getProperty("stdin.encoding")));

    System.out.println("Enter lines, finish with an empty line:");
    String line;
    while ((line = in.readLine()) != null && !line.isEmpty()) {
      System.out.println("You entered: " + line);
    }
  }
}
