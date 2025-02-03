package net.orekhov.paymentservice.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Модель платежа, представляющая информацию о платеже.
 * Содержит идентификатор платежа, статус, сумму и детали заказа.
 */
public class Payment {

    private static final Logger logger = LoggerFactory.getLogger(Payment.class); // Логгер для модели Payment

    // Идентификатор платежа
    @JsonProperty("paymentId")
    private String id;

    // Статус платежа (например, "pending", "completed", "failed")
    @JsonProperty("status")
    private PaymentStatus status;

    // Сумма платежа
    @JsonProperty("amount")
    private Double amount;

    // Детали заказа
    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("product")
    private String product;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price")
    private double price;

    /**
     * Конструктор для создания нового объекта платежа.
     * Генерирует новый UUID для идентификатора платежа.
     *
     * @param status    Статус платежа
     * @param amount    Сумма платежа
     * @param orderId   Идентификатор заказа
     * @param customerId Идентификатор клиента
     * @param product   Название товара
     * @param quantity  Количество товара
     * @param price     Цена товара
     */
    public Payment(PaymentStatus status, Double amount, String orderId, String customerId, String product, int quantity, double price) {
        this.id = UUID.randomUUID().toString(); // Генерация уникального идентификатора
        this.status = status;
        this.amount = amount;
        this.orderId = orderId;
        this.customerId = customerId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;

        logger.info("Created Payment: {}", this);
    }

    /**
     * Получить идентификатор платежа.
     *
     * @return Идентификатор платежа
     */
    public String getId() {
        return id;
    }

    /**
     * Установить идентификатор платежа.
     * (Не рекомендуется переопределять UUID)
     *
     * @param id Идентификатор платежа
     */
    public void setId(String id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Переопределение метода toString для удобного вывода информации о платеже.
     *
     * @return Строковое представление платежа
     */
    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", amount=" + amount +
                ", orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}

