package eu.happycoders.workingdirectory;

import java.io.File;
import java.io.IOException;

/** Runs pwd, so it needs Linux or macOS. */
public class Ch5ChildProcess {

  static void main() throws IOException, InterruptedException {
    System.out.print("Without directory(): ");
    new ProcessBuilder("pwd").inheritIO().start().waitFor();

    System.out.print("directory(\"target\"): ");
    new ProcessBuilder("pwd").directory(new File("target")).inheritIO().start().waitFor();
  }
}
