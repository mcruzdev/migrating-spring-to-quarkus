package dev.matheuscruz.quarkusclub.product;

import java.math.BigDecimal;

public record ProductOutput(String id, String name, BigDecimal price, Integer stock) {}
