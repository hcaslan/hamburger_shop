package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.model.ShoppingCart;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ShoppingService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;  // Jackson ObjectMapper

    @RabbitListener(queues = "getCart.Queue")
    public ShoppingCart getCartById(@Payload String profileId) {
        rabbitTemplate.convertAndSend("exchange.direct", "getCart.Route", profileId);
        Message message = new Message(profileId.getBytes());
        Message responseMessage = rabbitTemplate.sendAndReceive("exchange.direct", "getCart.Route", message);

        if (responseMessage != null) {
            byte[] body = responseMessage.getBody();
            return convertToShoppingCart(body);
        } else {
            throw new RuntimeException("Failed to retrieve shopping cart for profileId: " + profileId);
        }
    }

    private ShoppingCart convertToShoppingCart(byte[] body) {
        try {
            String json = new String(body, StandardCharsets.UTF_8);
            System.out.println("JSON: " + json); // Log the JSON for debugging
            return objectMapper.readValue(json, ShoppingCart.class);
        } catch (Exception e) {
            System.err.println("Error converting message body to ShoppingCart: " + e.getMessage());
            throw new RuntimeException("Error converting message body to ShoppingCart", e);
        }
    }
}
