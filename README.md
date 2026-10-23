# Java PrintWriter

## Article

The code refers to the following articles (not yet published):
* English: [Java PrintWriter](https://www.happycoders.eu/java/java-printwriter/)
* German: [Java PrintWriter](https://www.happycoders.eu/de/java/java-printwriter/)

## Contents

Most examples write the small log file `target/server.log`, the file the [BufferedReader article](https://www.happycoders.eu/java/java-bufferedreader/) reads. Every class with a `main()` method in `eu.happycoders.printwriter` is one chapter of the article and prints the outputs shown there. Run one from the repository root with `java -cp target/classes eu.happycoders.printwriter.<ClassName>` after `mvn compile`. Files the examples create go into `target/`.

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

## Benchmarks

The package `eu.happycoders.printwriter.benchmark` contains the JMH benchmarks of the article's performance section:

* `WriteBenchmark` – writing 1,222,747 lines, 100,000,000 bytes of UTF-8 text, with `BufferedWriter`, with a `PrintWriter` around `Files.newBufferedWriter()`, around a file name, and around a `FileWriter`, with autoFlush, and with a `PrintStream`; the lines are those of the test file of the [java-bufferedreader](https://github.com/SvenWoltmann/java-bufferedreader) repository
* `FormatBenchmark` – writing 1,000,000 log lines with `printf()`, with `println()` and string concatenation, and with one `print()` call per field, with and without a `BufferedWriter` below the `PrintWriter`
* `WriteCallCounter` – not JMH: counts the write calls that arrive at the `OutputStreamWriter` and at the `OutputStream` below it when a `PrintWriter` writes the test lines, with and without a `BufferedWriter` and with autoFlush

The benchmarks create the test text in memory from a fixed seed, so every machine writes the same bytes. Build and run:

```
mvn package
java -jar target/benchmarks.jar
```

The complete run takes about 15 minutes. Close other applications while it runs. The results of the article's run are in `results/apple-m5-pro/`.
