package dev.matheuscruz.quarkusclub.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

    @KafkaListener(topics = "${topic.name}", groupId = "${topic.groupName}")
    public void consume(String message) {
        LOGGER.info("📥 Received message from Kafka about new product: {}", message);
    }
}
