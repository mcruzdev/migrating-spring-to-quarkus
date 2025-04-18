package dev.matheuscruz.quarkusclub.product;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;

    public ProductProducer(KafkaTemplate<String, String> kafkaTemplate, @Value("${topic.name}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void send(String message) {
        this.kafkaTemplate.send(this.topicName, message).thenAccept(result -> {
            RecordMetadata metadata = result.getRecordMetadata();
            LOGGER.info("✅ [offset:{}][partition:{}] Message sent to topic: {}", metadata.offset(),
                    metadata.partition(), metadata.topic());
        });
    }
}
