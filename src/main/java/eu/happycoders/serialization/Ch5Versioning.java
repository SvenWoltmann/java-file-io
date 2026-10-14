package eu.happycoders.serialization;

import static eu.happycoders.serialization.util.Serialization.serialize;

import eu.happycoders.serialization.util.PackageSwitchingObjectInputStream;
import eu.happycoders.serialization.versioning.implicit.v1.Customer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectStreamClass;

public class Ch5Versioning {

  static void main() throws IOException, ClassNotFoundException {
    System.out.println("== Computed serialVersionUIDs");
    printUid(Customer.class);
    printUid(eu.happycoders.serialization.versioning.implicit.v2.Customer.class);
    printUid(eu.happycoders.serialization.versioning.implicit.v3.Customer.class);

    System.out.println("== Without serialVersionUID: method added");
    byte[] data = serialize(new Customer("Ada Lovelace", "ada@example.com"));
    read(data, Customer.class, eu.happycoders.serialization.versioning.implicit.v2.Customer.class);

    System.out.println("== With serialVersionUID: field added");
    data =
        serialize(
            new eu.happycoders.serialization.versioning.explicit.v1.Customer(
                "Ada Lovelace", "ada@example.com"));
    read(
        data,
        eu.happycoders.serialization.versioning.explicit.v1.Customer.class,
        eu.happycoders.serialization.versioning.explicit.v2.Customer.class);

    System.out.println("== With serialVersionUID: field removed");
    var withPhone =
        new eu.happycoders.serialization.versioning.explicit.v2.Customer(
            "Ada Lovelace", "ada@example.com");
    withPhone.setPhone("+44 20 7946 0000");
    read(
        serialize(withPhone),
        eu.happycoders.serialization.versioning.explicit.v2.Customer.class,
        eu.happycoders.serialization.versioning.explicit.v1.Customer.class);

    System.out.println("== With serialVersionUID: field renamed");
    read(
        data,
        eu.happycoders.serialization.versioning.explicit.v1.Customer.class,
        eu.happycoders.serialization.versioning.explicit.v3.Customer.class);

    System.out.println("== With serialVersionUID: field type changed");
    read(
        data,
        eu.happycoders.serialization.versioning.explicit.v1.Customer.class,
        eu.happycoders.serialization.versioning.explicit.v4.Customer.class);
  }

  private static void printUid(Class<?> type) {
    long uid = ObjectStreamClass.lookup(type).getSerialVersionUID();
    String packageName = type.getPackageName();
    String version = packageName.substring(packageName.lastIndexOf('.') + 1);
    System.out.println(version + ": " + uid);
  }

  private static void read(byte[] data, Class<?> writtenAs, Class<?> readAs)
      throws IOException, ClassNotFoundException {
    try (var in =
        new PackageSwitchingObjectInputStream(new ByteArrayInputStream(data), writtenAs, readAs)) {
      System.out.println(in.readObject());
    } catch (InvalidClassException | ClassCastException e) {
      System.out.println(e);
    }
  }
}
