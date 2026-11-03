package eu.happycoders.workingdirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Start it in target/ with APP_HOME=.. to see that it no longer depends on where it starts. */
public class Ch5BaseDirectory {

  static void main() throws IOException {
    String appHomeVariable = System.getenv().getOrDefault("APP_HOME", "");
    Path appHome = Path.of(appHomeVariable).toAbsolutePath().normalize();
    Path log = appHome.resolve("files/server.log");
    System.out.println("App home: " + appHome);
    System.out.println("Log file: " + log);
    System.out.println("Lines:    " + Files.readAllLines(log).size());
  }
}
