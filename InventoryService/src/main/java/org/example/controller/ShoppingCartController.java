package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.constant.Session;
import org.example.entity.CartItem;
import org.example.entity.ShoppingCart;
import org.example.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT+CART)
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping(ADDITEMTOCART)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ShoppingCart addItemToCart(@RequestParam String urunId,
                                      @RequestParam(required = false) List<String> extraOptions,
                                      @RequestParam(required = false) List<String> selectedOptions,
                                      @RequestParam(required = false) List<String> removedIngredients) {
        CartItem item = CartItem.builder()
                .urunId(urunId)
                .extraOptions(extraOptions)
                .selectedOptions(selectedOptions)
                .removedIngredients(removedIngredients)
                .build();
        return shoppingCartService.addItemToCart(Session.getProfileId(), item);
    }

    @GetMapping(GETCARTBYUSERID)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ShoppingCart getCart() {
        return shoppingCartService.getCartByUserId(Session.getProfileId());
    }

    @DeleteMapping(REMOVEITEMFROMCART)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ShoppingCart removeItemFromCart(@RequestParam String productId) {
        return shoppingCartService.removeItemFromCart(Session.getProfileId(), productId);
    }

    @PostMapping(CLEARCART)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ShoppingCart clearCart() {
        return shoppingCartService.clearCart(Session.getProfileId());
    }

}
