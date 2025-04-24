package dev.matheuscruz.quarkusclub.product;

import io.smallrye.reactive.messaging.kafka.KafkaClientService;
import io.smallrye.reactive.messaging.kafka.KafkaProducer;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ProductProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);

    final KafkaClientService kafkaClientService;
    final String topicName;

    public ProductProducer(final KafkaClientService kafkaClientService,
            final @ConfigProperty(name = "topic.name") String topicName) {
        this.kafkaClientService = kafkaClientService;
        this.topicName = topicName;
    }

    public void send(final String message) {
        KafkaProducer<String, String> producer = this.kafkaClientService.getProducer("product.producer");
        producer.send(new ProducerRecord<>(topicName, message)).subscribeAsCompletionStage().thenAccept(metadata -> {
            LOGGER.info("✅ [offset:{}][partition:{}] Message sent to topic: {}", metadata.offset(),
                    metadata.partition(), metadata.topic());
        });
    }
}
