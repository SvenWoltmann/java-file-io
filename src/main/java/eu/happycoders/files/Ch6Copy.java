package eu.happycoders.files;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.zip.GZIPInputStream;

public class Ch6Copy {

  static void main() throws IOException {
    Examples.reset();
    Path logDir = Path.of("target/logs");
    Path log = logDir.resolve("server.log");

    System.out.println("== copy() into a directory");
    Path archive = Files.createDirectory(logDir.resolve("archive"));
    Path copy = Files.copy(log, archive.resolve(log.getFileName()));
    System.out.println(copy + ": " + Files.size(copy) + " bytes");

    System.out.println("== copy() with the directory as target");
    try {
      Files.copy(log, archive);
    } catch (FileAlreadyExistsException e) {
      System.out.println(e);
    }

    System.out.println("== copy() onto an existing file");
    try {
      Files.copy(log, copy);
    } catch (FileAlreadyExistsException e) {
      System.out.println(e);
    }
    Files.copy(log, copy, REPLACE_EXISTING);
    System.out.println("Replaced: " + copy);

    System.out.println("== COPY_ATTRIBUTES");
    Files.setLastModifiedTime(log, FileTime.from(Instant.parse("2026-10-17T06:15:12Z")));
    Path plain = Files.copy(log, archive.resolve("plain.log"));
    Path withAttributes = Files.copy(log, archive.resolve("with-attributes.log"), COPY_ATTRIBUTES);
    System.out.println("Source:          " + Files.getLastModifiedTime(log));
    System.out.println("Copy:            " + Files.getLastModifiedTime(plain));
    System.out.println("COPY_ATTRIBUTES: " + Files.getLastModifiedTime(withAttributes));

    System.out.println("== a symbolic link as source");
    Path link = logDir.resolve("current.log");
    Files.createSymbolicLink(link, Path.of("server.log"));
    Path followed = Files.copy(link, archive.resolve("followed.log"));
    Path notFollowed = Files.copy(link, archive.resolve("not-followed.log"), NOFOLLOW_LINKS);
    System.out.println("isSymbolicLink(followed):    " + Files.isSymbolicLink(followed));
    System.out.println("isSymbolicLink(notFollowed): " + Files.isSymbolicLink(notFollowed));
    System.out.println("readSymbolicLink(notFollowed): " + Files.readSymbolicLink(notFollowed));

    System.out.println("== copy(InputStream, Path)");
    Path gz = Path.of("files/server.log.gz");
    Path unpacked = logDir.resolve("unpacked.log");
    try (InputStream in = new GZIPInputStream(Files.newInputStream(gz))) {
      long bytes = Files.copy(in, unpacked);
      System.out.println(bytes + " bytes copied");
    }

    System.out.println("== copy(Path, OutputStream)");
    Files.copy(unpacked, System.out);

    System.out.println("== mismatch()");
    System.out.println("mismatch(log, copy):     " + Files.mismatch(log, copy));
    String changed = Files.readString(copy).replaceFirst("INFO ", "WARN ");
    Files.writeString(copy, changed);
    System.out.println("after the change:        " + Files.mismatch(log, copy));
    System.out.println("isSameFile(log, copy):   " + Files.isSameFile(log, copy));
    System.out.println("isSameFile(log, link):   " + Files.isSameFile(log, link));
  }
}
