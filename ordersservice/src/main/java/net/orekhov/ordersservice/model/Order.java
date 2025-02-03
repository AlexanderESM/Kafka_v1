package net.orekhov.ordersservice.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс, представляющий заказ.
 * Этот класс содержит информацию о заказе, такую как идентификатор заказа, идентификатор клиента, товар,
 * количество, цена и статус заказа.
 */
public class Order {

    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    @JsonProperty("orderId")
    private String orderId; // Идентификатор заказа

    @JsonProperty("customerId")
    private String customerId; // Идентификатор клиента

    @JsonProperty("product")
    private String product; // Название товара

    @JsonProperty("quantity")
    private int quantity; // Количество товара

    @JsonProperty("price")
    private double price; // Цена товара

    @JsonProperty("status")
    private String status; // Статус заказа (например, "в обработке", "отправлен", "доставлен")

    /**
     * Конструктор для создания нового объекта заказа.
     *
     * @param orderId    Идентификатор заказа
     * @param customerId Идентификатор клиента
     * @param product    Название товара
     * @param quantity   Количество товара
     * @param price      Цена товара
     * @param status     Статус заказа
     */
    public Order(String orderId, String customerId, String product, int quantity, double price, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;

        // Логируем создание заказа
        logger.info("Created Order: {}", this);
    }

    // Геттеры и сеттеры

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        logger.info("Order ID changed from {} to {}", this.orderId, orderId);
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        logger.info("Customer ID changed from {} to {}", this.customerId, customerId);
        this.customerId = customerId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        logger.info("Product changed from {} to {}", this.product, product);
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        logger.info("Quantity changed from {} to {}", this.quantity, quantity);
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        logger.info("Price changed from {} to {}", this.price, price);
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        logger.info("Status changed from {} to {}", this.status, status);
        this.status = status;
    }

    /**
     * Переопределение метода toString для удобного вывода информации о заказе.
     *
     * @return Строковое представление заказа
     */
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}

