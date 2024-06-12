package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.entity.CartItem;
import org.example.entity.ShoppingCart;
import org.example.service.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.example.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT+CART)
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping(ADDITEMTOCART)
    @PreAuthorize("hasRole('USER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ShoppingCart addItemToCart(@RequestParam String userId, @RequestBody CartItem item) {
        return shoppingCartService.addItemToCart(userId, item);
    }













    @GetMapping(GETCARTBYUSERID)
    @PreAuthorize("hasRole('USER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ShoppingCart getCart(@RequestParam String userId) {
        return shoppingCartService.getCartByUserId(userId);
    }

    @DeleteMapping(REMOVEITEMFROMCART)
    @PreAuthorize("hasRole('USER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ShoppingCart removeItemFromCart(@RequestParam String userId, @RequestParam String productId) {
        return shoppingCartService.removeItemFromCart(userId, productId);
    }

    @PostMapping(CLEARCART)
    @PreAuthorize("hasRole('USER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ShoppingCart clearCart(@RequestParam String userId) {
        return shoppingCartService.clearCart(userId);
    }
}
