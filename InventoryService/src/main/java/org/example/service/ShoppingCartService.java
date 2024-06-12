package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Urun;
import org.example.entity.UrunSecenekler;
import org.example.repository.UrunRepository;
import org.example.entity.CartItem;
import org.example.entity.ShoppingCart;
import org.example.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UrunRepository productRepository;

    public ShoppingCart getCartByUserId(String userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    public ShoppingCart addItemToCart(String userId, CartItem item) {
        // Ürünün detaylarını al
        Optional<Urun> productOpt = productRepository.findById(item.getProductId());
        if (productOpt.isPresent()) {
            Urun product = productOpt.get();

            // Ürünün temel fiyatını al
            double basePrice = product.getFiyat();

            // Ekstraların fiyatlarını hesapla
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

            // Toplam fiyatı hesapla
            double totalPrice = basePrice + extraPrice;
            item.setFiyat(totalPrice);

            // Sepete ekle
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
}
