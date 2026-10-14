package eu.happycoders.serialization.special.unchecked;

import java.io.Serializable;
import java.math.BigDecimal;

/** The same components as the validating {@code OrderItem}, without the validation. */
public record OrderItem(String product, int quantity, BigDecimal price) implements Serializable {}
