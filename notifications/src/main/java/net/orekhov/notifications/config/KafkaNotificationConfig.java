package net.orekhov.notifications.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import java.util.Map;


/**
 * Конфигурация Kafka для сервиса
 * Этот класс настраивает потребителей Kafka, которые будут слушать темы Kafka и обрабатывать сообщения.
 * В частности, конфигурируется десериализация строковых сообщений и настройка фабрики контейнера слушателя Kafka.
 */
@Configuration
public class KafkaNotificationConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaNotificationConfig.class); // Логгер для класса

    // Извлекаем group-id из application.properties
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    /**
     * Бин для создания ConsumerFactory.
     * Используется для настройки потребителя сообщений в Kafka.
     *
     * @param kafkaProperties Свойства Kafka, автоматически подгружаемые Spring Boot.
     * @return ConsumerFactory, который используется для создания KafkaListener.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory(KafkaProperties kafkaProperties) {
        // Извлекаем свойства из KafkaProperties
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();

        // Указываем, что мы используем десериализатор для строк
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Указываем group-id для потребителя, если он не установлен
        props.putIfAbsent(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        // Создаем и возвращаем ConsumerFactory с заданными параметрами
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Бин для настройки KafkaListenerContainerFactory.
     * Фабрика контейнера слушателя управляет слушателями Kafka и их контейнерами.
     *
     * @param consumerFactory ConsumerFactory, используемый для создания слушателей.
     * @return KafkaListenerContainerFactory, настроенная для обработки строковых сообщений.
     */
    @Bean
    public KafkaListenerContainerFactory<?> listenerFactory(ConsumerFactory<String, String> consumerFactory) {
        // Создаем фабрику контейнеров слушателей
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

        // Устанавливаем фабрику потребителей
        factory.setConsumerFactory(consumerFactory);

        // Устанавливаем флаг, что слушатель не должен обрабатывать сообщения пакетами (batch)
        factory.setBatchListener(false);

        // Возвращаем настроенную фабрику
        return factory;
    }
}

