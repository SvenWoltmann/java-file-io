package eu.happycoders.workingdirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Ch1Reading {

  static void main() throws IOException {
    System.out.println("== Path.of(\"\") and new File(\"\")");
    System.out.println("user.dir:     " + System.getProperty("user.dir"));
    System.out.println("Path.of(\"\"):  " + Path.of("").toAbsolutePath());
    System.out.println("new File(\"\"): " + new File("").getAbsolutePath());

    System.out.println("== Path.of(\".\") and new File(\".\")");
    Path dot = Path.of(".").toAbsolutePath();
    File dotFile = new File(".");
    System.out.println("Path.of(\".\"):         " + dot);
    System.out.println("  normalize():        " + dot.normalize());
    System.out.println("new File(\".\"):        " + dotFile.getAbsolutePath());
    System.out.println("  getCanonicalPath(): " + dotFile.getCanonicalPath());
    Path empty = Path.of("").toAbsolutePath();
    System.out.println("dot.equals(empty):    " + dot.equals(empty));
  }
}
