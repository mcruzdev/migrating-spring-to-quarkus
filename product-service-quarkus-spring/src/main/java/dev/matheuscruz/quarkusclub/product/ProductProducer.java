package dev.matheuscruz.quarkusclub.product;

import io.smallrye.reactive.messaging.kafka.KafkaClientService;
import io.smallrye.reactive.messaging.kafka.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);

    private final KafkaClientService kafkaClientService;
    private final String topicName;

    public ProductProducer(KafkaClientService kafkaClientService, @Value("${topic.name}") String topicName) {
        this.kafkaClientService = kafkaClientService;
        this.topicName = topicName;
    }

    public void send(String message) {
        KafkaProducer<String, String> producer = this.kafkaClientService.getProducer("product.producer");
        producer.send(new ProducerRecord<>(topicName, message)).subscribeAsCompletionStage().thenAccept(metadata -> {
            LOGGER.info("✅ [offset:{}][partition:{}] Message sent to topic: {}", metadata.offset(),
                    metadata.partition(), metadata.topic());
        });
    }
}
