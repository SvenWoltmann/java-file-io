package eu.happycoders.workingdirectory;

import java.nio.file.Path;

public class Intro {

  static void main() {
    Path workingDirectory = Path.of("").toAbsolutePath();
    System.out.println(workingDirectory);
    System.out.println(System.getProperty("user.dir"));
  }
}
