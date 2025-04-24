package dev.matheuscruz.quarkusclub.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@ApplicationScoped
public class CreateProduct {

    static final Logger LOGGER = LoggerFactory.getLogger(CreateProduct.class);
    final ProductRepository productRepository;
    final ProductProducer productProducer;
    final ObjectMapper objectMapper;

    @Inject
    public CreateProduct(ProductRepository productRepository, ProductProducer productProducer,
            ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.productProducer = productProducer;
        this.objectMapper = objectMapper;
    }

    public Output execute(Input input) {
        Product product = new Product(input.name(), input.price(), input.stock());

        QuarkusTransaction.requiringNew().run(() -> this.productRepository.persist(product));

        try {
            String message = this.objectMapper.writeValueAsString(product);
            this.productProducer.send(message);
        } catch (Exception e) {
            LOGGER.error("Fire and regret, good luck friend.", e);
        }

        return new Output(product.getId());
    }

    public record Input(String name, BigDecimal price, Integer stock) {
    }

    public record Output(String id) {
    }
}
