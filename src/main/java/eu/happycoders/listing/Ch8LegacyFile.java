package eu.happycoders.listing;

import java.io.File;
import java.util.Arrays;

public class Ch8LegacyFile {

  static void main() {
    File project = new File("files/project");

    System.out.println("== File.list()");
    System.out.println(Arrays.toString(project.list()));

    System.out.println("== File.listFiles()");
    for (File file : project.listFiles()) {
      String info = file.isDirectory() ? "/" : " (" + file.length() + " bytes)";
      System.out.println(file + info);
    }

    System.out.println("== File.listFiles() on a missing directory");
    System.out.println(new File("files/project/test").listFiles());
  }
}
