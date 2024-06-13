package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.constant.Session;
import org.example.entity.Receipt;
import org.example.entity.ShoppingCart;
import org.example.service.ShoppingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.example.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT + SHOPPING)
@RequiredArgsConstructor
public class ShoppingController {
    private final ShoppingService shoppingService;

    @GetMapping(GETCARTBYID)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ShoppingCart> getCartById () {
        return ResponseEntity.ok(shoppingService.getCartById(Session.getProfileId()));
    }

    @PostMapping(CHECKOUT)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Receipt> checkout(@RequestParam String addressId) {
        return ResponseEntity.ok(shoppingService.checkout(Session.getProfileId(), addressId));
    }
}
