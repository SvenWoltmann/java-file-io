package eu.happycoders.workingdirectory;

/** Start it in /tmp on macOS, where /tmp is a symbolic link to /private/tmp. */
public class Ch1SymbolicLink {

  static void main() {
    System.out.println("user.dir: " + System.getProperty("user.dir"));
    System.out.println("PWD:      " + System.getenv("PWD"));
  }
}
