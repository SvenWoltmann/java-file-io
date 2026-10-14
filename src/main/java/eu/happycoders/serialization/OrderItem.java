package eu.happycoders.serialization;

import java.io.Serializable;
import java.math.BigDecimal;

public record OrderItem(String product, int quantity, BigDecimal price) implements Serializable {}
