package net.orekhov.ordersservice.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.orekhov.ordersservice.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Сервис для отправки сообщений в Kafka.
 * Этот класс отвечает за отправку сообщений в Kafka топик "new_orders".
 */
@Service
public class OrderProducer {

    // Название Kafka топика, в который будут отправляться сообщения
    private static final String TOPIC = "new_orders";

    // Логгер для класса OrderProducer
    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);

    // Автосвязывание KafkaTemplate для работы с Kafka
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Автосвязывание ObjectMapper для преобразования объектов в JSON
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Метод для отправки сообщения в Kafka топик.
     *
     * Этот метод принимает строку сообщения, логирует её и отправляет в топик Kafka.
     *
     * @param message Сообщение, которое будет отправлено в Kafka.
     */
    public void sendMessage(String message) {
        // Логируем информацию о том, что сообщение отправляется в Kafka
        logger.info("Отправил сообщение в топик '{}': {}", TOPIC, message);

        // Отправляем сообщение в Kafka топик
        kafkaTemplate.send(TOPIC, message);

        // Логируем успешную отправку сообщения
        logger.info("Сообщение успешно отправлено в топик '{}'.", TOPIC);
    }

}

