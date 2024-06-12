package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.entity.Urun;
import org.example.entity.UrunSecenekler;
import org.example.repository.UrunRepository;
import org.example.entity.CartItem;
import org.example.entity.ShoppingCart;
import org.example.repository.ShoppingCartRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UrunRepository productRepository;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public ShoppingCart getCartByUserId(String userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    public ShoppingCart addItemToCart(String userId, CartItem item) {
        System.out.println("additemCart içinde");
        // Ürünün detaylarını al
        Optional<Urun> productOpt = productRepository.findById(item.getProductId());
        if (productOpt.isPresent()) {
            Urun product = productOpt.get();

            // Ürünün temel fiyatını al
            double basePrice = product.getFiyat();

            System.out.println("Ekstraların fiyatlarını hesapla");
            double extraPrice = 0.0;
            if (item.getSelectedOptions() != null) {
                for (String option : item.getSelectedOptions()) {
                    UrunSecenekler secenek = product.getSecenekler().stream()
                            .filter(s -> s.getAd().equals(option))
                            .findFirst()
                            .orElse(null);
                    if (secenek != null) {
                        extraPrice += secenek.getEkstraFiyat();
                    }
                }
            }
            System.out.println("Toplam fiyatı hesapla");
            double totalPrice = basePrice + extraPrice;
            item.setFiyat(totalPrice);

            System.out.println("Sepete ekle");
            ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
            if (cart == null) {
                cart = new ShoppingCart();
                cart.setUserId(userId);
            }
            cart.getItems().add(item);
            return shoppingCartRepository.save(cart);
        }
        return null;
    }

    public ShoppingCart removeItemFromCart(String userId, String productId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart != null) {
            cart.getItems().removeIf(item -> item.getProductId().equals(productId));
            return shoppingCartRepository.save(cart);
        }
        return null;
    }

    public ShoppingCart clearCart(String userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart != null) {
            cart.getItems().clear();
            return shoppingCartRepository.save(cart);
        }
        return null;
    }
    @RabbitListener(queues = "getCart.Queue")
    public void getCart(@Payload String profileId) {
        // Fetch the shopping cart
        ShoppingCart cart = shoppingCartRepository.findByUserId(profileId);

        if (cart != null) {
            try {
                // Convert the cart object to JSON bytes
                byte[] cartBytes = objectMapper.writeValueAsBytes(cart);

                // Create a message with the cartBytes as payload
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                Message message = new Message(cartBytes, messageProperties);

                // Send the message back to the requester
                // Assuming you have a specific exchange and routing key to send back the response
                rabbitTemplate.convertAndSend("exchange.direct", "response.routing.key", message);

            } catch (Exception e) {
                throw new RuntimeException("Error converting ShoppingCart to bytes", e);
            }
        } else {
            throw new RuntimeException("No shopping cart found for profileId: " + profileId);
        }
    }
}
