package eu.happycoders.workingdirectory;

import java.io.File;

/** Run on Java 24 and on Java 25 to see the difference (JDK-8024695). */
public class Ch1EmptyFile {

  static void main() {
    File empty = new File("");
    String[] entries = empty.list();
    System.out.println("getAbsolutePath(): " + empty.getAbsolutePath());
    System.out.println("exists():          " + empty.exists());
    System.out.println("isDirectory():     " + empty.isDirectory());
    System.out.println(
        "list():            " + (entries == null ? "null" : entries.length + " entries"));
  }
}
