package eu.happycoders.printwriter;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Ch7StringWriter {

  static void main() {
    System.out.println("== PrintWriter around a StringWriter");
    StringWriter buffer = new StringWriter();
    try (PrintWriter out = new PrintWriter(buffer)) {
      for (LogEntry entry : LogEntry.SERVER_LOG) {
        if (!entry.level().equals("INFO")) {
          out.printf("%-5s %s%n", entry.level(), entry.message());
        }
      }
      System.out.print(buffer);
    }

    System.out.println("== A stack trace as a string");
    try {
      Integer.parseInt("8O80");
    } catch (NumberFormatException e) {
      StringWriter trace = new StringWriter();
      try (PrintWriter out = new PrintWriter(trace)) {
        e.printStackTrace(out);
      }
      String text = trace.toString();
      System.out.println(text.lines().findFirst().orElseThrow());
      System.out.println(text.lines().count() + " lines");
    }

    System.out.println("== The same with formatted() and StringBuilder");
    StringBuilder text = new StringBuilder();
    for (LogEntry entry : LogEntry.SERVER_LOG) {
      if (!entry.level().equals("INFO")) {
        text.append("%-5s %s%n".formatted(entry.level(), entry.message()));
      }
    }
    System.out.print(text);
  }
}
