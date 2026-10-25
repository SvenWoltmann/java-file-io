package eu.happycoders.files;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/** Two threads check whether a file exists and create it if not – 10,000 times. */
public class Ch2ExistsRace {

  private static final int FILES = 10_000;

  static void main() throws IOException, InterruptedException {
    Examples.reset();
    Path dir = Path.of("target/logs/race");
    Files.createDirectory(dir);

    AtomicInteger collisions = new AtomicInteger();
    CountDownLatch start = new CountDownLatch(1);
    Runnable checkThenCreate =
        () -> {
          try {
            start.await();
            for (int i = 0; i < FILES; i++) {
              Path file = dir.resolve("file-" + i + ".log");
              if (Files.notExists(file)) {
                try {
                  Files.createFile(file);
                } catch (FileAlreadyExistsException e) {
                  collisions.incrementAndGet();
                }
              }
            }
          } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
          }
        };

    Thread first = Thread.ofPlatform().start(checkThenCreate);
    Thread second = Thread.ofPlatform().start(checkThenCreate);
    start.countDown();
    first.join();
    second.join();

    System.out.println(
        FILES + " files, " + collisions + " times notExists() was true and createFile() failed");
  }
}
