package eu.happycoders.workingdirectory;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Start it in the repository root with -Duser.dir set to the absolute path of target/. */
public class Ch4UserDirOption {

  static void main() throws IOException {
    System.out.println("user.dir: " + System.getProperty("user.dir"));

    Path viaNio = Path.of("via-nio.txt");
    Files.writeString(viaNio, "written with Files.writeString()\n");

    File viaIo = new File("via-io.txt");
    try (FileOutputStream out = new FileOutputStream(viaIo)) {
      out.write("written with FileOutputStream\n".getBytes(UTF_8));
    }

    System.out.println("viaNio.toAbsolutePath():");
    System.out.println("  " + viaNio.toAbsolutePath());
    System.out.println("viaIo.getAbsolutePath():");
    System.out.println("  " + viaIo.getAbsolutePath());
    System.out.println("viaIo.exists():                " + viaIo.exists());
    System.out.println("Files.exists(viaIo.toPath()): " + Files.exists(viaIo.toPath()));
  }
}
