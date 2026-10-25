package eu.happycoders.printwriter;

import java.util.List;

/** One line of the log file the examples write. */
public record LogEntry(String timestamp, String level, String message) {

  /** The five entries of the log file {@code server.log} from the BufferedReader article. */
  public static final List<LogEntry> SERVER_LOG =
      List.of(
          new LogEntry("2026-10-17 08:15:02", "INFO", "Server started on port 8080"),
          new LogEntry("2026-10-17 08:15:07", "INFO", "GET /java/java-bufferedreader/ 200"),
          new LogEntry("2026-10-17 08:15:09", "WARN", "GET /java/bufferedreader/ 404"),
          new LogEntry("2026-10-17 08:15:11", "INFO", "GET /java/java-streams/ 200"),
          new LogEntry("2026-10-17 08:15:12", "ERROR", "Connection reset by peer"));
}
