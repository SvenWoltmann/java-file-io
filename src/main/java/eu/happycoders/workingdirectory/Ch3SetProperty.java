package eu.happycoders.workingdirectory;

import java.io.File;
import java.nio.file.Path;

public class Ch3SetProperty {

  static void main() {
    System.out.println("Before setProperty():");
    System.out.println("  user.dir: " + System.getProperty("user.dir"));
    System.out.println("  File:     " + new File("notes.txt").getAbsolutePath());
    System.out.println("  Path:     " + Path.of("notes.txt").toAbsolutePath());

    System.setProperty("user.dir", "/tmp");

    System.out.println("After setProperty():");
    System.out.println("  user.dir: " + System.getProperty("user.dir"));
    System.out.println("  File:     " + new File("notes.txt").getAbsolutePath());
    System.out.println("  Path:     " + Path.of("notes.txt").toAbsolutePath());
  }
}
