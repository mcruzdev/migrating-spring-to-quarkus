package dev.matheuscruz.quarkusclub.product;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Service
// we have conflict between @Dependent and @Singleton
public class ProductConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

    @Incoming("product.created.v1")
    public void consume(String message) {
        LOGGER.info("📥 Received message from Kafka about new product: {}", message);
    }
}