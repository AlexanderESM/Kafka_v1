package net.orekhov.shippingservice.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.orekhov.shippingservice.model.BuildStatus;
import net.orekhov.shippingservice.model.Shipping;
import net.orekhov.shippingservice.model.ShippingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ShippingConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ShippingConsumer.class);
    private final ObjectMapper objectMapper;
    private final ShippingProducer shippingProducer;

    @Autowired
    public ShippingConsumer(ObjectMapper objectMapper, ShippingProducer shippingProducer) {
        this.objectMapper = objectMapper;
        this.shippingProducer = shippingProducer;
    }

    @KafkaListener(topics = "payed_orders", groupId = "shipping_group")
    public void consumeMessage(String message) {
        logger.info("Получено сообщение из Kafka: {}", message);

        try {
            // Десериализация JSON в объект Shipping
            Shipping shipping = objectMapper.readValue(message, Shipping.class);
            logger.info("Десериализованный объект Shipping: {}", shipping);

            // Обновляем статусы отправки и сборки
            shipping.setShippingStatus(ShippingStatus.DELIVERED);  // Статус отправки
            shipping.setBuildStatus(BuildStatus.COMPLETED);    // Статус сборки

            // Логируем обновленные статусы
            logger.info("Обновленный статус отправки: {}", shipping.getShippingStatus());
            logger.info("Обновленный статус сборки: {}", shipping.getBuildStatus());

            // Сериализация Shipping в JSON
            String shippingJson = objectMapper.writeValueAsString(shipping);

            // Отправка сообщения в Kafka через ShippingProducer
            shippingProducer.sendMessage(shippingJson);

        } catch (Exception e) {
            logger.error("Ошибка при десериализации сообщения: ", e);
        }
    }
}
