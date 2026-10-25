package eu.happycoders.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch11Links {

  static void main() throws IOException {
    Examples.reset();
    Path logDir = Path.of("target/logs");
    Path log = logDir.resolve("server.log");

    System.out.println("== createSymbolicLink()");
    Path link = logDir.resolve("current.log");
    Files.createSymbolicLink(link, Path.of("server.log"));
    System.out.println("readSymbolicLink(): " + Files.readSymbolicLink(link));
    System.out.println("isSymbolicLink():   " + Files.isSymbolicLink(link));
    System.out.println("size(link):         " + Files.size(link));
    System.out.println("isSameFile():       " + Files.isSameFile(link, log));

    System.out.println("== createLink()");
    Path hardLink = Files.createLink(logDir.resolve("server-hardlink.log"), log);
    System.out.println("isSymbolicLink():   " + Files.isSymbolicLink(hardLink));
    System.out.println("isSameFile():       " + Files.isSameFile(hardLink, log));

    System.out.println("== after deleting the original");
    Files.delete(log);
    System.out.println("size(hardLink): " + Files.size(hardLink));
    System.out.println("exists(link):   " + Files.exists(link));
  }
}
