package eu.happycoders.files;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ch8Move {

  static void main() throws IOException {
    Examples.reset();
    Path logDir = Path.of("target/logs");
    Path log = logDir.resolve("server.log");

    System.out.println("== move() into a directory");
    Path archive = Files.createDirectory(logDir.resolve("archive"));
    Path archived = Files.move(log, archive.resolve(log.getFileName()));
    Examples.printTree(logDir);

    System.out.println("== rename with resolveSibling()");
    Path renamed = Files.move(archived, archived.resolveSibling("server.log.1"));
    Examples.printTree(logDir);

    System.out.println("== move() a directory");
    Path old = Files.move(archive, logDir.resolve("old"));
    Examples.printTree(logDir);

    System.out.println("== move() onto an existing file");
    Files.copy(Path.of("files/server.log"), log);
    Path target = old.resolve("server.log.1");
    try {
      Files.move(log, target);
    } catch (FileAlreadyExistsException e) {
      System.out.println(e);
    }
    Files.move(log, target, REPLACE_EXISTING);
    Examples.printTree(logDir);

    System.out.println("== write to a temp file, then ATOMIC_MOVE");
    Path config = logDir.resolve("app.properties");
    Files.writeString(config, "port=8080\n");
    Path temp = Files.createTempFile(logDir, "app-", ".tmp");
    Files.writeString(temp, "port=8443\n");
    Files.move(temp, config, ATOMIC_MOVE);
    System.out.print(Files.readString(config));
    Examples.printTree(logDir);
  }
}
