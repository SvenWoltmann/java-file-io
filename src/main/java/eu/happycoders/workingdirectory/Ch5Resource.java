package eu.happycoders.workingdirectory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Start it in any directory: the file comes from the class path, not the working directory. */
public class Ch5Resource {

  static void main() throws IOException {
    Properties defaults = new Properties();
    try (InputStream in = Ch5Resource.class.getResourceAsStream("defaults.properties")) {
      defaults.load(in);
    }
    System.out.println("user.dir: " + System.getProperty("user.dir"));
    System.out.println("Defaults: " + defaults);
  }
}
