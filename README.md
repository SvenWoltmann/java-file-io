# Java: Create, Copy, Move, Rename, and Delete Files and Directories

[![Build](https://github.com/SvenWoltmann/java-create-copy-move-delete-files/actions/workflows/build.yml/badge.svg)](https://github.com/SvenWoltmann/java-create-copy-move-delete-files/actions/workflows/build.yml)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-create-copy-move-delete-files&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-create-copy-move-delete-files)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-create-copy-move-delete-files&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-create-copy-move-delete-files)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-create-copy-move-delete-files&metric=security_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-create-copy-move-delete-files)

## Article

The code refers to the following articles:
* English: [How to Create, Copy, Move, and Delete Files and Directories in Java](https://www.happycoders.eu/java/how-to-list-directory-contents-move-copy-delete-files/)
* German: [Dateien und Verzeichnisse erstellen, kopieren, verschieben und löschen](https://www.happycoders.eu/de/java/verzeichnisse-listen-dateien-verschieben-kopieren-loeschen/)

## Contents

Every example starts with a fresh directory `target/logs/` that holds a copy of `files/server.log`, the log file of the [BufferedReader article](https://www.happycoders.eu/java/java-bufferedreader/). `Examples.reset()` creates that directory, and `Examples.printTree()` prints what is in it after an operation. Every class with a `main()` method in `eu.happycoders.files` is one chapter of the article and prints the outputs shown there. Run one from the repository root with `java -cp target/classes eu.happycoders.files.<ClassName>` after `mvn compile`.

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

The examples use Java 25 language features (`main()` without arguments) and therefore need Java 25 or newer. `Ch2Exists`, `Ch3CreateFile`, and `Ch5TempFiles` set or read POSIX permissions and run on Linux and macOS only.

## Benchmark

The package `eu.happycoders.files.benchmark` contains the JMH benchmark of the article's performance section:

* `CopyBenchmark` – copies a file of 100,000,000 bytes with `Files.copy()`, with `Files.copy()` and `COPY_ATTRIBUTES` (a clone on APFS since Java 20), with `InputStream.transferTo()`, with `FileChannel.transferTo()`, and with a buffer loop of 8,192 bytes

Build and run:

```
mvn package
java -jar target/benchmarks.jar
```

The complete run takes about five minutes. Close other applications while it runs. The outputs of the example classes from the article's run are in `results/outputs-jdk25.txt`.
