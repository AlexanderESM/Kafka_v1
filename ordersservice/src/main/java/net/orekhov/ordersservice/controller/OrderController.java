package net.orekhov.ordersservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.orekhov.ordersservice.kafka.OrderProducer;
import net.orekhov.ordersservice.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для обработки запросов, связанных с заказами.
 * Этот контроллер предоставляет API для создания заказов и получения статуса заказов.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderProducer orderProducer;
    private final ObjectMapper objectMapper; // Объект для сериализации JSON

    /**
     * Конструктор контроллера.
     *
     * @param orderProducer сервис для отправки заказов в Kafka
     * @param objectMapper  сервис для преобразования объектов в JSON
     */
    public OrderController(OrderProducer orderProducer, ObjectMapper objectMapper) {
        this.orderProducer = orderProducer;
        this.objectMapper = objectMapper;
    }

    /**
     * Обработчик POST-запроса для создания нового заказа.
     * Принимает объект заказа в теле запроса, конвертирует его и отправляет в Kafka.
     *
     * @param order объект заказа, который будет создан и отправлен в Kafka.
     * @return ответ с сообщением, подтверждающим создание заказа и его отправку в Kafka.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        logger.info("#1 Creating order with ID: {}", order.getOrderId());
        logger.info("#2 getOrderId: {}", order.getCustomerId());
        logger.info("#3 getProduct: {}", order.getProduct());
        logger.info("#4 getProduct: {}", order.getQuantity());
        logger.info("#5 getProduct: {}", order.getPrice());
        logger.info("#6 getProduct: {}", order.getStatus());

        try {
            // Преобразуем объект заказа в JSON
            String orderMessage = objectMapper.writeValueAsString(order);

            // Отправляем заказ в Kafka
            orderProducer.sendMessage(orderMessage);

            logger.info("Order with ID: {} created and sent to Kafka.", order.getOrderId());

            return ResponseEntity.ok("Order created and sent to Kafka: " + order.getOrderId());
        } catch (JsonProcessingException e) {
            logger.error("Error converting order to JSON", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing order");
        }
    }

    /**
     * Обработчик GET-запроса для получения статуса заказа по его ID.
     *
     * @param orderId идентификатор заказа, статус которого нужно получить.
     * @return ответ с текущим статусом заказа.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String orderId) {
        logger.info("Fetching status for order with ID: {}", orderId);

        // Получаем данные о заказе через сервис (пока заглушка)
        String status = "Processing";  // Пример статуса, замените на реальный вызов сервиса

        logger.info("Order ID: {} has status: {}", orderId, status);

        // Возвращаем ответ с статусом заказа
        return ResponseEntity.ok("Order Status: " + status);
    }
}
