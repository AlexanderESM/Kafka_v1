package net.orekhov.kafkacommon;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableKafka // Аннотация для активации KafkaListener
public class KafkacommonApplication {
    private static final Logger logger = LoggerFactory.getLogger(KafkacommonApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(KafkacommonApplication.class, args);
    }

    // Чтение значений из application.properties
    @Value("${kafka.topic.payment-orders}")
    private String paymentOrdersTopic;

    @Value("${kafka.topic.new-orders}")
    private String newOrdersTopic;

    @Value("${kafka.topic.shipped-orders}")
    private String shippedOrdersTopic;

    /**
     * Бин для KafkaAdmin, который используется для управления топиками.
     * Устанавливаем параметры подключения к Kafka-брокеру.
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        logger.info("Initializing KafkaAdmin with bootstrap servers: localhost:9092");
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    /**
     * Бин для создания топика, используя конфигурацию из application.properties
     */
    @Bean
    public NewTopic payedOrdersTopic() {
        logger.info("Creating topic: {}", paymentOrdersTopic);
        return new NewTopic(paymentOrdersTopic, 3, (short) 1); // Использование значений из application.properties
    }

    /**
     * Бин для создания топика, используя конфигурацию из application.properties
     */
    @Bean
    public NewTopic newOrdersTopic() {
        logger.info("Creating topic: {}", newOrdersTopic);
        return new NewTopic(newOrdersTopic, 3, (short) 1);
    }

    /**
     * Бин для создания топика, используя конфигурацию из application.properties
     */
    @Bean
    public NewTopic shippedOrdersTopic() {
        logger.info("Creating topic: {}", shippedOrdersTopic);
        return new NewTopic(shippedOrdersTopic, 3, (short) 1);
    }

    /**
     * Конфигурация для Kafka-потребителя, включая настройки подключения и десериализацию сообщений.
     */
    @Bean
    public Map<String, Object> consumerConfigs() {
        logger.info("Configuring Kafka consumer with bootstrap servers: localhost:9092");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    /**
     * Бин для создания Kafka ConsumerFactory, используя указанные настройки.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        logger.info("Creating KafkaConsumerFactory");
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * Бин для создания KafkaListenerContainerFactory с настройками потребителя.
     * Устанавливаем количество параллельных потоков (concurrency).
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        logger.info("Creating KafkaListenerContainerFactory with concurrency: 3");
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3); // Устанавливаем количество параллельных слушателей
        return factory;
    }

    /**
     * Конфигурация для Kafka-производителя, включая настройки подключения и сериализацию сообщений.
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        logger.info("Configuring Kafka producer with bootstrap servers: localhost:9092");
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    /**
     * Бин для создания Kafka ProducerFactory, используя указанные настройки.
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        logger.info("Creating KafkaProducerFactory");
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Бин для создания KafkaTemplate, который будет использоваться для отправки сообщений в Kafka.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        logger.info("Creating KafkaTemplate");
        return new KafkaTemplate<>(producerFactory());
    }

}
