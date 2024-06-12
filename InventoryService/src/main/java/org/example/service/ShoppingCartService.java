package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.repository.UrunRepository;
import org.example.repository.ShoppingCartRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
            cart.setTotalPrice(cart.getTotalPrice() + totalPrice);
            return shoppingCartRepository.save(cart);
        }
        return null;
    }


    public ShoppingCart removeItemFromCart(String userId, String id) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart != null) {
            CartItem first = cart.getItems().stream().filter(item -> item.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Urun bulunamadi"));
            cart.getItems().removeIf(item -> item.getId().equals(id));
            double v = cart.getTotalPrice() - first.getFiyat();
            cart.setTotalPrice(v);
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
    public ShoppingCart handleRequest(String id) {
        return shoppingCartRepository.findByUserId(id);
    }

    @RabbitListener(queues = "deleteCart.Queue")
    public void deleteCart(ShoppingCart cart) {
        shoppingCartRepository.delete(cart);
    }

}
