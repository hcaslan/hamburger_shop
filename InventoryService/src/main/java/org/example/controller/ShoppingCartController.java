package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.CartItem;
import org.example.entity.ShoppingCart;
import org.example.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ShoppingCart getCart(@PathVariable String userId) {
        return shoppingCartService.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public ShoppingCart addItemToCart(@PathVariable String userId, @RequestBody CartItem item) {
        return shoppingCartService.addItemToCart(userId, item);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ShoppingCart removeItemFromCart(@PathVariable String userId, @PathVariable String productId) {
        return shoppingCartService.removeItemFromCart(userId, productId);
    }

    @PostMapping("/{userId}/clear")
    public ShoppingCart clearCart(@PathVariable String userId) {
        return shoppingCartService.clearCart(userId);
    }
}
