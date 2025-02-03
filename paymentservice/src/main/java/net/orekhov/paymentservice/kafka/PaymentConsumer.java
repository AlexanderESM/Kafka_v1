package net.orekhov.paymentservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.orekhov.paymentservice.model.OrderToPaymentConverter;
import net.orekhov.paymentservice.model.Payment;
import net.orekhov.paymentservice.model.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentConsumer.class);
    private final ObjectMapper objectMapper;
    private final PaymentProducer paymentProducer;
    private final OrderToPaymentConverter orderToPaymentConverter;

    public PaymentConsumer(ObjectMapper objectMapper, PaymentProducer paymentProducer, OrderToPaymentConverter orderToPaymentConverter) {
        this.objectMapper = objectMapper;
        this.paymentProducer = paymentProducer;
        this.orderToPaymentConverter = orderToPaymentConverter;
    }

    @KafkaListener(topics = "new_orders", groupId = "orders_group")
    public void consumeMessage(String message) {
        logger.info("Получено сообщение из Kafka: {}", message);

        try {
            // Десериализация JSON в объект Payment
            Payment payment = objectMapper.readValue(message, Payment.class);
            logger.info("Создан объект Payment: {}", payment);

            // Устанавливаем статус платежа и сумму
            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setAmount(payment.getPrice());

            // Логирование всех полей объекта Payment
            logger.info("Payment Details: ID={}, Status={}, Amount={}, OrderID={}, CustomerID={}, Product={}, Quantity={}, Price={}",
                    payment.getId(), payment.getStatus(), payment.getAmount(), payment.getOrderId(),
                    payment.getCustomerId(), payment.getProduct(), payment.getQuantity(), payment.getPrice());

            // Сериализация Payment в JSON
            String paymentJson = objectMapper.writeValueAsString(payment);

            // Отправка JSON в Kafka
            paymentProducer.sendMessage(paymentJson);
            logger.info("Payment sent to Kafka: {}", paymentJson);

        } catch (JsonProcessingException e) {
            logger.error("Ошибка при обработке JSON", e);
        }
    }
}

