package eu.happycoders.files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Intro {

  static void main() throws IOException {
    Examples.reset();

    Path logDir = Path.of("target/logs");
    Path log = logDir.resolve("server.log");

    Files.createDirectories(logDir);
    if (Files.exists(log)) {
      Files.move(log, logDir.resolve("server.log.1"), REPLACE_EXISTING);
    }
    Files.createFile(log);

    Examples.printTree(logDir);
  }
}
