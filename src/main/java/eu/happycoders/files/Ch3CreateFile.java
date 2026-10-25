package eu.happycoders.files;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class Ch3CreateFile {

  static void main() throws IOException {
    Examples.reset();
    Path logDir = Path.of("target/logs");

    System.out.println("== createFile()");
    Path app = logDir.resolve("app.log");
    Files.createFile(app);
    System.out.println(app + ": " + Files.size(app) + " bytes");

    System.out.println("== createFile() on an existing file");
    try {
      Files.createFile(app);
    } catch (FileAlreadyExistsException e) {
      System.out.println(e);
    }

    System.out.println("== createFile() in a missing directory");
    try {
      Files.createFile(Path.of("target/logs/2026/app.log"));
    } catch (NoSuchFileException e) {
      System.out.println(e);
    }

    System.out.println("== create if not exists");
    for (int run = 1; run <= 2; run++) {
      Path audit = logDir.resolve("audit.log");
      try {
        Files.createFile(audit);
        System.out.println("Run " + run + ": created " + audit);
      } catch (FileAlreadyExistsException e) {
        System.out.println("Run " + run + ": " + audit + " already exists");
      }
    }

    System.out.println("== writeString() creates the file");
    Path notes = logDir.resolve("notes.txt");
    Files.writeString(notes, "Rotated at 08:16\n");
    System.out.println(notes + ": " + Files.size(notes) + " bytes");
    try {
      Files.writeString(notes, "Rotated at 08:16\n", CREATE_NEW);
    } catch (FileAlreadyExistsException e) {
      System.out.println(e);
    }

    System.out.println("== createFile() with permissions");
    FileAttribute<Set<PosixFilePermission>> ownerOnly =
        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-------"));
    Path secret = Files.createFile(logDir.resolve("secret.log"), ownerOnly);
    System.out.println(PosixFilePermissions.toString(Files.getPosixFilePermissions(secret)));
    System.out.println(PosixFilePermissions.toString(Files.getPosixFilePermissions(app)));

    System.out.println("== File.createNewFile()");
    File legacy = new File("target/logs/app.log");
    System.out.println("createNewFile(): " + legacy.createNewFile());
  }
}
