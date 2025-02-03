package net.orekhov.notifications.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    @KafkaListener(topics = "sent_orders", groupId = "notification_group")
    public void consumeMessage(String message) {
        logger.info("Получено сообщение из Kafka: {}", message);
        // Логика обработки сообщения
        logger.info(" todo....Отправляет уведомления пользователям о том, что их заказ был успешно доставлен. ");
    }
}

