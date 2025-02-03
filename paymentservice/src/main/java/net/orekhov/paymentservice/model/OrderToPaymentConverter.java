package net.orekhov.paymentservice.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderToPaymentConverter {

    private static final Logger logger = LoggerFactory.getLogger(OrderToPaymentConverter.class);
    private final ObjectMapper objectMapper;

    public OrderToPaymentConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


}
