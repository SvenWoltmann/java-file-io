package eu.happycoders.serialization.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Reads the classes of one package as the classes of another package with the same simple names.
 *
 * <p>The examples keep two versions of a class side by side, e.g. {@code v1.Customer} and {@code
 * v2.Customer}. Writing with the first and reading with the second simulates a class that changed
 * between writing and reading. Serialization compares class names without their package, so the
 * stream written by {@code v1.Customer} is checked against {@code v2.Customer} as if it were the
 * same class.
 */
public class PackageSwitchingObjectInputStream extends ObjectInputStream {

  private final String fromPackage;
  private final String toPackage;

  public PackageSwitchingObjectInputStream(InputStream in, Class<?> from, Class<?> to)
      throws IOException {
    super(in);
    this.fromPackage = from.getPackageName() + ".";
    this.toPackage = to.getPackageName() + ".";
  }

  @Override
  protected Class<?> resolveClass(ObjectStreamClass desc)
      throws IOException, ClassNotFoundException {
    String name = desc.getName();
    if (name.startsWith(fromPackage)) {
      String switched = toPackage + name.substring(fromPackage.length());
      return Class.forName(switched, false, getClass().getClassLoader());
    }
    return super.resolveClass(desc);
  }
}
