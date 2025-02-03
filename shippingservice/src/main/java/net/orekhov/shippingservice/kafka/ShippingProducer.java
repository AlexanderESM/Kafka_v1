package net.orekhov.shippingservice.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShippingProducer {

    private static final Logger logger = LoggerFactory.getLogger(ShippingProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    // Конструктор для инъекции зависимостей
    public ShippingProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    // Отправка сообщения в Kafka
    public void sendMessage(String message) {
        // Отправляем сообщение в Kafka топик
        kafkaTemplate.send("sent_orders", message);
        // Логируем успешную отправку сообщения
        logger.info("Сообщение успешно отправлено в топик '{}'.", "sent_orders");
    }
}
