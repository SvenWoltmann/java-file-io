package eu.happycoders.serialization;

import static eu.happycoders.serialization.util.Serialization.serialize;

import eu.happycoders.serialization.versioning.misdeclared.Customer;
import java.io.IOException;

/**
 * Serializes a class with a misdeclared serialVersionUID. Run it with JFR to see the
 * jdk.SerializationMisdeclaration event:
 *
 * <pre>
 * java -XX:StartFlightRecording:settings=profile,filename=misdeclaration.jfr \
 *     -cp target/classes eu.happycoders.serialization.Ch5Misdeclaration
 * jfr print --events jdk.SerializationMisdeclaration misdeclaration.jfr
 * </pre>
 */
public class Ch5Misdeclaration {

  static void main() throws IOException {
    byte[] data = serialize(new Customer("Ada Lovelace", "ada@example.com"));
    System.out.println(data.length + " bytes written");
  }
}
