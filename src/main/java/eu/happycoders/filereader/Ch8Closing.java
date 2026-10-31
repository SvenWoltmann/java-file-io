package eu.happycoders.filereader;

import com.sun.management.UnixOperatingSystemMXBean;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class Ch8Closing {

  static void main() throws IOException, InterruptedException {
    System.out.println("== Reading after close()");
    FileReader closed = new FileReader("files/server.log");
    closed.close();
    try {
      closed.read();
    } catch (IOException e) {
      System.out.println(e);
    }

    System.out.println("== FileReaders that are never closed");
    // works on Linux and macOS; Windows has no UnixOperatingSystemMXBean
    UnixOperatingSystemMXBean os =
        (UnixOperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    System.out.println("Open files at the start:   " + os.getOpenFileDescriptorCount());
    for (int i = 0; i < 100; i++) {
      FileReader reader = new FileReader("files/server.log");
      reader.read();
    }
    System.out.println("After 100 FileReaders:     " + os.getOpenFileDescriptorCount());
    System.gc();
    Thread.sleep(100);
    System.out.println("After a garbage collection: " + os.getOpenFileDescriptorCount());
  }
}
