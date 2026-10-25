package eu.happycoders.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

public class Ch5TempFiles {

  static void main() throws IOException {
    Examples.reset();
    Path logDir = Path.of("target/logs");

    System.out.println("== createTempFile() in the temp directory");
    System.out.println("java.io.tmpdir = " + System.getProperty("java.io.tmpdir"));
    for (int i = 0; i < 3; i++) {
      Path tempFile = Files.createTempFile("upload-", ".part");
      System.out.println(
          tempFile.getFileName()
              + " "
              + Files.size(tempFile)
              + " bytes "
              + PosixFilePermissions.toString(Files.getPosixFilePermissions(tempFile)));
      Files.delete(tempFile);
    }

    System.out.println("== createTempFile() in a directory of your choice");
    Path part = Files.createTempFile(logDir, "server-", ".part");
    System.out.println(part);

    System.out.println("== createTempDirectory()");
    Path workDir = Files.createTempDirectory("rotation-");
    System.out.println(
        workDir.getFileName()
            + " "
            + PosixFilePermissions.toString(Files.getPosixFilePermissions(workDir)));
    Files.delete(workDir);

    System.out.println("== clean up with try/finally");
    Path tempFile = Files.createTempFile("upload-", ".part");
    try {
      Files.writeString(tempFile, "2026-10-17 08:15:02 INFO  Server started on port 8080\n");
      System.out.println("Written: " + Files.size(tempFile) + " bytes");
    } finally {
      Files.delete(tempFile);
    }
    System.out.println("exists(tempFile): " + Files.exists(tempFile));
  }
}
