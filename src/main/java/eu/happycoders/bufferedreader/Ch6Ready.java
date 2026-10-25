package eu.happycoders.bufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.UncheckedIOException;

public class Ch6Ready {

  static void main() throws IOException, InterruptedException {
    PipedWriter writer = new PipedWriter();
    try (BufferedReader reader = new BufferedReader(new PipedReader(writer))) {
      Thread producer =
          Thread.startVirtualThread(
              () -> {
                try (writer) {
                  Thread.sleep(500);
                  writer.write("Hello\n");
                } catch (IOException e) {
                  throw new UncheckedIOException(e);
                } catch (InterruptedException _) {
                  Thread.currentThread().interrupt();
                }
              });

      System.out.println("ready():    " + reader.ready());
      System.out.println("readLine(): " + reader.readLine());
      System.out.println("readLine(): " + reader.readLine());
      producer.join();
    }
  }
}
