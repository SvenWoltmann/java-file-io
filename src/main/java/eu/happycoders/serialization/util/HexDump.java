package eu.happycoders.serialization.util;

import java.util.HexFormat;

/** Prints bytes as hex and as characters, 16 bytes per line. */
public final class HexDump {

  private HexDump() {}

  public static void print(byte[] bytes, int length) {
    HexFormat hex = HexFormat.ofDelimiter(" ");
    for (int offset = 0; offset < length; offset += 16) {
      int end = Math.min(offset + 16, length);
      StringBuilder chars = new StringBuilder();
      for (int i = offset; i < end; i++) {
        int b = bytes[i] & 0xFF;
        chars.append(b >= 0x20 && b < 0x7F ? (char) b : '.');
      }
      System.out.printf("%04x  %-47s  %s%n", offset, hex.formatHex(bytes, offset, end), chars);
    }
  }
}
