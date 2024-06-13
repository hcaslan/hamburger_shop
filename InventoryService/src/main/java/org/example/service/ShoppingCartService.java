package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.exceptions.ErrorType;
import org.example.exceptions.InventoryMicroServiceException;
import org.example.repository.UrunRepository;
import org.example.repository.ShoppingCartRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UrunRepository productRepository;

    public ShoppingCart getCartByUserId(String profileId) {
        return shoppingCartRepository.findByProfileId(profileId).orElseThrow(() -> new InventoryMicroServiceException(ErrorType.CART_NOT_FOUND));
    }

    public ShoppingCart addItemToCart(String profileId, CartItem item) {
        System.out.println("additemCart içinde");
        // Ürünün detaylarını al
        Urun product = productRepository.findById(item.getUrunId()).orElseThrow(() -> new InventoryMicroServiceException(ErrorType.URUN_NOT_FOUND));

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
        item.setAd(product.getAd());

        System.out.println("Sepete ekle");
        ShoppingCart cart = shoppingCartRepository.findByProfileId(profileId).orElse(null);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setProfileId(profileId);
        }
        cart.getItems().add(item);
        cart.setTotalPrice(cart.getTotalPrice() + totalPrice);
        return shoppingCartRepository.save(cart);
    }


    public ShoppingCart removeItemFromCart(String profileId, String id) {
        ShoppingCart cart = shoppingCartRepository.findByProfileId(profileId).orElseThrow(() -> new InventoryMicroServiceException(ErrorType.CART_NOT_FOUND));
        if (cart != null) {
            CartItem first = cart.getItems().stream().filter(item -> item.getId().equals(id)).findFirst().orElseThrow(() -> new InventoryMicroServiceException(ErrorType.URUN_NOT_FOUND));
            cart.getItems().removeIf(item -> item.getId().equals(id));
            double v = cart.getTotalPrice() - first.getFiyat();
            cart.setTotalPrice(v);
            return shoppingCartRepository.save(cart);
        }
        throw new InventoryMicroServiceException(ErrorType.CART_IS_EMPTY);
    }

    public ShoppingCart clearCart(String profileId) {
        ShoppingCart cart = shoppingCartRepository.findByProfileId(profileId).orElseThrow(() -> new InventoryMicroServiceException(ErrorType.CART_NOT_FOUND));
        if (cart != null) {
            cart.getItems().clear();
            cart.setTotalPrice(0.0);
            return shoppingCartRepository.save(cart);
        }
        throw new InventoryMicroServiceException(ErrorType.CART_IS_EMPTY);
    }



    @RabbitListener(queues = "getCart.Queue")
    @Transactional
    public ShoppingCart handleRequest(String id) {
        return shoppingCartRepository.findByProfileId(id).orElseThrow(() -> new InventoryMicroServiceException(ErrorType.CART_NOT_FOUND));
    }

    @RabbitListener(queues = "deleteCart.Queue")
    @Transactional
    public void deleteCart(ShoppingCart cart) {
        shoppingCartRepository.delete(cart);
    }

}
