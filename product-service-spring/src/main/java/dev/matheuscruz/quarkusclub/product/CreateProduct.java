package dev.matheuscruz.quarkusclub.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateProduct {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProduct.class);
    private final ProductRepository productRepository;
    private final ProductProducer productProducer;
    private final ObjectMapper objectMapper;
    private final RunInTransaction runInTx;

    public CreateProduct(ProductRepository productRepository, ProductProducer productProducer,
            ObjectMapper objectMapper, RunInTransaction runInTx) {
        this.productRepository = productRepository;
        this.productProducer = productProducer;
        this.objectMapper = objectMapper;
        this.runInTx = runInTx;
    }

    public Output execute(Input input) {
        Product product = new Product(input.name(), input.price(), input.stock());

        this.runInTx.run(() -> this.productRepository.save(product));

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
