# Java PrintWriter

[![Build](https://github.com/SvenWoltmann/java-printwriter/actions/workflows/build.yml/badge.svg)](https://github.com/SvenWoltmann/java-printwriter/actions/workflows/build.yml)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-printwriter&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-printwriter)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-printwriter&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-printwriter)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-printwriter&metric=security_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-printwriter)

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
