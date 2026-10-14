# Java Serialization

[![Build](https://github.com/SvenWoltmann/java-serialization/actions/workflows/build.yml/badge.svg)](https://github.com/SvenWoltmann/java-serialization/actions/workflows/build.yml)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-serialization&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-serialization)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-serialization&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-serialization)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-serialization&metric=security_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-serialization)

## Article

The code refers to the following articles:
* English: [Serialization in Java: Serializable, transient, serialVersionUID](https://www.happycoders.eu/java/java-serialization/)
* German: [Serialisierung in Java: Serializable, transient, serialVersionUID](https://www.happycoders.eu/de/java/java-serialisierung/)

## Contents

The examples work on a small order model: the classes `Order` and `Customer` and the record `OrderItem`. Chapters that need a variant of a class – one that is not serializable, has no `serialVersionUID`, or validates in `readObject()` – keep it in a package of its own (`graph`, `transients`, `versioning`, `inheritance`, `custom`, `special`, `filter`).

Every class with a `main()` method is one chapter of the article and prints the outputs shown there. Run one with `java -cp target/classes eu.happycoders.serialization.<ClassName>` after `mvn compile`. The examples that write files write them into the current directory, with the extension `.ser`.

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
* `Ch9Collections` – `ArrayList` capacity, `List.of()`, views such as `keySet()` and `subList()`
* `Ch10Filters` – deserialization filters from patterns, the missing `!*`, and the limits `maxdepth` (against Joshua Bloch’s deserialization bomb) and `maxarray`
* `Ch10FilterFactory` – a filter factory that applies a filter within a `ScopedValue` scope
* `Ch10GlobalFilter` – a JVM-wide filter with `-Djdk.serialFilter` (the command is in the class comment; add `-Djava.util.logging.config.file=serialfilter-logging.properties` to log rejected classes)
* `Ch11Size` – the size of an order with `ObjectOutputStream` and with `DataOutputStream`

The class `PackageSwitchingObjectInputStream` in `eu.happycoders.serialization.util` reads a stream written by one version of a class with another version of it: the chapters keep both versions side by side, e.g. `versioning.explicit.v1.Customer` and `versioning.explicit.v2.Customer`. Serialization compares class names without their package, so this simulates a class that changed between writing and reading.


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
