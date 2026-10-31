package eu.happycoders.filereader;

import java.io.FileReader;
import java.io.IOException;

public class Ch2ReadArray {

  static void main() throws IOException {
    System.out.println("== read(char[])");
    try (FileReader reader = new FileReader("files/server.log")) {
      char[] buffer = new char[100];
      int length;
      while ((length = reader.read(buffer)) != -1) {
        System.out.println("read " + length + " characters");
      }
    }

    System.out.println("== Only the first length characters are new");
    StringBuilder correct = new StringBuilder();
    StringBuilder wholeArray = new StringBuilder();
    try (FileReader reader = new FileReader("files/server.log")) {
      char[] buffer = new char[100];
      int length;
      while ((length = reader.read(buffer)) != -1) {
        correct.append(buffer, 0, length);
        wholeArray.append(buffer);
      }
    }
    System.out.println("correct:     " + correct.length() + " characters");
    System.out.println("whole array: " + wholeArray.length() + " characters");
    System.out.println(wholeArray.substring(wholeArray.lastIndexOf("\n") + 1));
  }
}
