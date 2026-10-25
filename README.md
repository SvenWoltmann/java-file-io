# Java File I/O

[![Build](https://github.com/SvenWoltmann/java-file-io/actions/workflows/build.yml/badge.svg)](https://github.com/SvenWoltmann/java-file-io/actions/workflows/build.yml)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-file-io&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-file-io)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-file-io&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-file-io)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-file-io&metric=security_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-file-io)

The code of the HappyCoders.eu article series on files in Java – one package per article. Every class with a `main()` method is one chapter of its article and prints the outputs shown there.

## Articles

| Article | Package |
|---|---|
| Java BufferedReader ([English](https://www.happycoders.eu/java/java-bufferedreader/), [German](https://www.happycoders.eu/de/java/java-bufferedreader/)) | `eu.happycoders.bufferedreader` |
| Java PrintWriter ([English](https://www.happycoders.eu/java/java-printwriter/), [German](https://www.happycoders.eu/de/java/java-printwriter/)) | `eu.happycoders.printwriter` |
| Serialization in Java ([English](https://www.happycoders.eu/java/java-serialization/), [German](https://www.happycoders.eu/de/java/java-serialisierung/)) | `eu.happycoders.serialization` |
| Creating, copying, moving, and deleting files and directories (not yet published; [German](https://www.happycoders.eu/de/java/verzeichnisse-listen-dateien-verschieben-kopieren-loeschen/), [English](https://www.happycoders.eu/java/how-to-list-directory-contents-move-copy-delete-files/)) | `eu.happycoders.files` |
| Listing directory contents (not yet published; [German](https://www.happycoders.eu/de/java/java-verzeichnis-auslesen/), [English](https://www.happycoders.eu/java/java-list-files-in-directory/)) | `eu.happycoders.listing` |

## Running the examples

The examples use Java 25 language features and need Java 25 or newer. Build once with `mvn compile`, then run a class from the repository root:

```
java -cp target/classes eu.happycoders.<package>.<ClassName>
```

Files the examples create go into `target/`; the serialization examples write their `.ser` files into the current directory.

## Java BufferedReader – `eu.happycoders.bufferedreader`

Most examples read the small log file `files/server.log`.

* `Intro` – reads the log file line by line
* `Ch1Creating` – `Files.newBufferedReader()`, `FileReader`, `InputStreamReader` around a `GZIPInputStream`, an own buffer size, and a Latin-1 file read as UTF-8
* `Ch2ReadLine` – line terminators, empty lines, and the end of the input
* `Ch3Lines` – `lines()` with `filter()` and `groupingBy()`, an `UncheckedIOException` while streaming, and `Files.lines()`
* `Ch4ReadAll` – `readAllLines()` and `readAllAsString()` (Java 25), also after a `readLine()`
* `Ch5Characters` – `read()` and `read(char[], int, int)`
* `Ch6MarkReset` – an optional header line with `mark()` and `reset()`, and `skip()`
* `Ch6Ready` – `ready()` returns `false`, yet the next `readLine()` returns a line
* `Ch7Console` – reading lines from `System.in` with the charset from `stdin.encoding` (Java 25)
* `Ch7Readln` – `IO.readln()` (Java 25)
* `Ch8Scanner` – tokens with `Scanner` and with `split()`, and how both classes report an `IOException`
* `Ch9Closing` – closing the `BufferedReader` closes the reader inside it
* `Ch10Mistakes` – calling `readLine()` twice per loop iteration

## Java PrintWriter – `eu.happycoders.printwriter`

Most examples write the small log file `target/server.log`, the file the BufferedReader examples read.

* `Intro` – writes the log file with `printf()`
* `Ch1Creating` – `new PrintWriter(String)` with and without a charset, a `PrintWriter` around `Files.newBufferedWriter()`, and one around a `GZIPOutputStream`
* `Ch1WriteCalls` – counts the write calls that arrive below a `PrintWriter`, with and without a `BufferedWriter` in between
* `Ch1Append` – appending to a file: `APPEND` alone, `CREATE` and `APPEND`, and a `FileWriter` with `append = true`
* `Ch2Print` – `print()` and `println()` for different types and `null`, the line separator, `write(int)` vs. `print(int)`, and `append()`
* `Ch3Printf` – format strings, the locale, chained `printf()` calls, and a missing argument
* `Ch4Flush` – when the bytes reach the file, with and without autoFlush
* `Ch5CheckError` – a failing writer, writing after `close()`, and the euro sign in ISO-8859-1
* `Ch6Closing` – closing the `PrintWriter` closes the writer inside it, and what closing a `PrintWriter` around `System.out` does
* `Ch7StringWriter` – text and a stack trace into a string, and the same with `formatted()` and `StringBuilder`
* `Ch8Console` – a `PrintWriter` around `System.out` and `System.console().writer()`
* `Ch9PrintStream` – how often a `PrintStream` and a `PrintWriter`, both with autoFlush, flush their stream
* `Ch10Mistakes` – a `PrintWriter` that is never closed leaves the file empty

## Serialization – `eu.happycoders.serialization`

The examples work on a small order model: the classes `Order` and `Customer` and the record `OrderItem`. Chapters that need a variant of a class – one that is not serializable, has no `serialVersionUID`, or validates in `readObject()` – keep it in a package of its own (`graph`, `transients`, `versioning`, `inheritance`, `custom`, `special`, `filter`).

* `Intro` – writes an order into a file and reads it back
* `Ch1Writing` – `ObjectOutputStream`: writing into a byte array, the first bytes of the stream, buffering
* `Ch2Reading` – `ObjectInputStream`: pattern matching on the result, several objects in one stream, buffering, a file that is not a serialization stream
* `Ch3ObjectGraph` – `NotSerializableException`, shared references, the same object written twice, `reset()` and `writeUnshared()`
* `Ch4Transient` – `transient` fields after deserialization and how `readObject()` restores them
* `Ch5Versioning` – the computed `serialVersionUID` and what happens when a class changes between writing and reading
* `Ch5Misdeclaration` – a misdeclared `serialVersionUID`, for the JFR event `jdk.SerializationMisdeclaration` (the command is in the class comment)
* `Ch6Inheritance` – a serializable class with a non-serializable superclass
* `Ch7Custom` – validation in `readObject()`, `writeObject()` for the superclass state, `readResolve()`, the serialization proxy
* `Ch8Special` – records, enums (including a renamed constant), lambdas, and inner classes
* `Ch8LambdaNames` – a serializable lambda written with `write` and read with `read`; renaming its variable and recompiling makes the old stream unreadable (the steps are in the class comment)
* `Ch9Collections` – `ArrayList` capacity, `List.of()`, views such as `keySet()` and `subList()`
* `Ch10Filters` – deserialization filters from patterns, the missing `!*`, and the limits `maxdepth` (against Joshua Bloch’s deserialization bomb) and `maxarray`
* `Ch10FilterFactory` – a filter factory that applies a filter within a `ScopedValue` scope
* `Ch10GlobalFilter` – a JVM-wide filter with `-Djdk.serialFilter` (the command is in the class comment; add `-Djava.util.logging.config.file=serialfilter-logging.properties` to log rejected classes)
* `Ch11Size` – the size of an order with `ObjectOutputStream` and with `DataOutputStream`

The class `PackageSwitchingObjectInputStream` in `eu.happycoders.serialization.util` reads a stream written by one version of a class with another version of it: the chapters keep both versions side by side, e.g. `versioning.explicit.v1.Customer` and `versioning.explicit.v2.Customer`. Serialization compares class names without their package, so this simulates a class that changed between writing and reading.

## Creating, copying, moving, and deleting files – `eu.happycoders.files`

Every example starts with a fresh directory `target/logs/` that holds a copy of `files/server.log`. `Examples.reset()` creates that directory, and `Examples.printTree()` prints what is in it after an operation. The outputs of the article's run are in `results/files/outputs-jdk25.txt`. `Ch2Exists`, `Ch3CreateFile`, and `Ch5TempFiles` set or read POSIX permissions and run on Linux and macOS only.

* `Intro` – rotates the log file: create the directory, move the old log aside, create a new one
* `Ch1FilesVsFile` – `File.renameTo()` and `File.delete()` return `false`, `Files.move()` and `Files.delete()` say why
* `Ch2Exists` – `exists()`, `notExists()`, `isRegularFile()`, `isDirectory()`, a dangling symbolic link, and a directory in which existence cannot be determined
* `Ch2ExistsRace` – two threads check whether a file exists and create it if not, 10,000 times: how often the check was already outdated
* `Ch3CreateFile` – `createFile()`, its exceptions, create-if-not-exists, `writeString()` with `CREATE_NEW`, POSIX permissions, `File.createNewFile()`
* `Ch4CreateDirectory` – `createDirectory()` vs. `createDirectories()`, their exceptions, `File.mkdir()` and `mkdirs()`
* `Ch5TempFiles` – `createTempFile()`, `createTempDirectory()`, their permissions, cleaning up with try/finally
* `Ch6Copy` – `copy()` into a directory, onto an existing file, with `COPY_ATTRIBUTES` and `NOFOLLOW_LINKS`, from an `InputStream`, to an `OutputStream`, and `mismatch()`
* `Ch7CopyDirectory` – `copy()` on a directory creates only an empty one; `copyDirectory()` with `walkFileTree()`
* `Ch8Move` – `move()` into a directory, renaming with `resolveSibling()`, moving a directory, `REPLACE_EXISTING`, and writing to a temp file followed by `ATOMIC_MOVE`
* `Ch9Delete` – `delete()`, `deleteIfExists()`, `DirectoryNotEmptyException`, deleting a symbolic link, `File.delete()`
* `Ch10DeleteDirectory` – `deleteDirectory()` with `walkFileTree()` and with `walk()`, and a missing directory
* `Ch11Links` – `createSymbolicLink()`, `readSymbolicLink()`, `createLink()`, and what survives deleting the original

## Listing directory contents – `eu.happycoders.listing`

The examples list the directory `files/project/`, a small Maven project with five files in three subdirectories. Directories that some examples create themselves end up under `target/`.

* `Intro` – `Files.list()` on the project directory
* `Ch1List` – `list()` is not recursive, the order is the file system's, `sorted()`, filtering for directories, `NotDirectoryException` and `NoSuchFileException`
* `Ch1DirectoryStream` – `newDirectoryStream()` with the globs `*.md` and `*.{md,xml}`
* `Ch1OpenStreams` – opens streams without closing them until the process runs out of file handles
* `Ch2ListRecursive` – finding `.java` files with `list()` and recursion written by hand
* `Ch3Walk` – `walk()`, its depth-first order, `maxDepth` 1 and 0, `FOLLOW_LINKS`, and a `FileSystemLoopException`
* `Ch4WalkFileTree` – `walkFileTree()` with a `SimpleFileVisitor`, a `LoggingVisitor` that numbers every call, `SKIP_SUBTREE`, and `TERMINATE`
* `Ch5Find` – `find()` by file name, by size, and with `maxDepth` 1
* `Ch6PathMatcher` – `glob:**/*.java`, a glob on the file name only, and a `regex:` matcher
* `Ch7AccessDenied` – how `walk()`, `find()`, `walkFileTree()` with and without `visitFileFailed()`, and `list()` with recursion react to a directory without read permission; sets POSIX permissions and runs on Linux and macOS only
* `Ch8LegacyFile` – `File.list()` and `File.listFiles()`, which return `null` for a missing directory

The outputs of the article's run are in `results/listing/outputs-jdk25.txt`.

## Benchmarks

Each article's JMH benchmarks are in the `benchmark` subpackage of its package:

* `ReadBenchmark` – reading a 100,000,000-byte UTF-8 text file with `InputStreamReader`, `FileReader`, and `BufferedReader` (`read()`, `read(char[])`, `readLine()`, `lines()`, `readAllLines()`), and with `Scanner.nextLine()`
* `BufferSizeBenchmark` – `readLine()` with buffer sizes from 256 to 1,048,576 characters
* `ReadCallCounter` – not JMH: counts how often the `InputStreamReader` reads from the file when a `BufferedReader` reads it line by line, for buffer sizes from 256 to 1,048,576 characters; the count is the same for every size
* `DecoderWarmupCheck` – not JMH: decodes the test file with an `InputStreamReader` in blocks of one size after warming the JVM up with another, one JVM per call; it shows that the speed depends on the warm-up block size, not on the measured one (the command is in the class comment)
* `WriteBenchmark` – writing 1,222,747 lines, 100,000,000 bytes of UTF-8 text, with `BufferedWriter`, with a `PrintWriter` around `Files.newBufferedWriter()`, around a file name, and around a `FileWriter`, with autoFlush, and with a `PrintStream`; the lines are those of the test file that `ReadBenchmark` reads
* `FormatBenchmark` – writing 1,000,000 log lines with `printf()`, with `println()` and string concatenation, and with one `print()` call per field, with and without a `BufferedWriter` below the `PrintWriter`
* `WriteCallCounter` – not JMH: counts the write calls that arrive at the `OutputStreamWriter` and at the `OutputStream` below it when a `PrintWriter` writes the test lines, with and without a `BufferedWriter` and with autoFlush
* `CopyBenchmark` – copies a file of 100,000,000 bytes with `Files.copy()`, with `Files.copy()` and `COPY_ATTRIBUTES` (a clone on APFS since Java 20), with `InputStream.transferTo()`, with `FileChannel.transferTo()`, and with a buffer loop of 8,192 bytes

`mvn package` builds `target/benchmarks.jar` with all of them. Run the benchmarks of one article with its package as filter – a bare class name like `ReadBenchmark` can match more than you expect:

```
mvn package
java -jar target/benchmarks.jar eu.happycoders.bufferedreader.benchmark
java -jar target/benchmarks.jar eu.happycoders.printwriter.benchmark
java -jar target/benchmarks.jar eu.happycoders.files.benchmark
```

The BufferedReader and PrintWriter runs take about 15 minutes each, the copy benchmark about five minutes. Close other applications while they run. The results of the articles' runs are in `results/<package>/`, e.g. `results/printwriter/apple-m5-pro/`.


<!-- happycoders-resources:start -->
<!-- Generated from happycoders-website-astro/data/github-readme by scripts/content/sync-github-readmes.mjs. Edit there, not here. -->

## <br>Additional Resources

### <br>Java Versions PDF Cheat Sheet

**Stay up-to-date** with the latest Java features with this **free** [PDF Cheat Sheet](https://www.happycoders.eu/java-versions/)!

[<img src="/img/java-versions-cheat-sheet-mockup.png" alt="Java Versions PDF Cheat Sheet Mockup" width="468">](https://www.happycoders.eu/java-versions/)

* Avoid lengthy research with this **concise overview of all Java versions from Java 10 to Java 27**.
* **Discover the innovative features** of each new Java version, summarized on a single page.
* **Impress your team** with your up-to-date knowledge of the latest Java version.

👉 [Download the free Java Versions PDF](https://www.happycoders.eu/java-versions/)<br>

_(Hier geht's zur deutschen Version &rarr; [Java-Versionen PDF](https://www.happycoders.eu/de/java-versionen/))_


### <br>The Big O Cheat Sheet

With this **free** [1-page PDF cheat sheet](https://www.happycoders.eu/big-o-cheat-sheet/), you'll always have the **7 most important complexity classes** at a glance.

[<img src="/img/big-o-cheat-sheet-mockup.png" alt="Big O PDF Cheat Sheet Mockup" width="304">](https://www.happycoders.eu/big-o-cheat-sheet/)

* **Always choose the most efficient data structures** and thus increase the performance of your applications.
* **Be prepared for technical interviews** and confidently present your algorithm knowledge.
* **Become a sought-after problem solver** and be known for systematically tackling complex problems.

👉 [Download the free Big O Cheat Sheet](https://www.happycoders.eu/big-o-cheat-sheet/)<br>

_(Hier geht's zur deutschen Version &rarr; [O-Notation Cheat Sheet](https://www.happycoders.eu/de/o-notation-cheat-sheet/))_


### <br>HappyCoders Newsletter
👉 Want to stay on top of modern Java?
Sign up for the [HappyCoders newsletter](https://www.happycoders.eu/newsletter/) – Modern Java: new versions & features, performance, and JVM insights – once a month.

_(Hier geht's zur deutschen Version &rarr; [HappyCoders-Newsletter deutsch](https://www.happycoders.eu/de/newsletter/))_


### <br>🇩🇪 An alle Java-Entwickler:innen, die durch fundierte Kenntnisse über Datenstrukturen besseren Code schreiben wollen

Trage dich jetzt unverbindlich auf die [Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/) von „Mastering Data Structures in Java“ ein, und erhalte das beste Angebot!

[<img src="/img/mastering-data-structures-product-mockup.png" alt="Mastering Data Structures Mockup" width="640">](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)

👉 [Zur Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)
<!-- happycoders-resources:end -->
