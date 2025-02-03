package net.orekhov.paymentservice.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.payed-orders}")
    private String TOPIC;

    // Конструктор для инъекции зависимостей
    public PaymentProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    // Отправка сообщения в Kafka
    public void sendMessage(String message) {
        // Отправляем сообщение в Kafka топик
        kafkaTemplate.send(TOPIC, message);
        // Логируем успешную отправку сообщения
        logger.info("Сообщение успешно отправлено в топик '{}'.", TOPIC);
    }
}
