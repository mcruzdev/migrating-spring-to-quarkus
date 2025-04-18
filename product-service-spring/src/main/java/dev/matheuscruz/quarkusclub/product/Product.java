package dev.matheuscruz.quarkusclub.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private Integer stock;

    // necessary for JPA
    protected Product() {
    }

    Product(final String name, final BigDecimal price, final Integer stock) {
        // some domain validations here...
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
}
