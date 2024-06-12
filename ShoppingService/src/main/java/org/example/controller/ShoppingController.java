package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Receipt;
import org.example.entity.ShoppingCart;
import org.example.service.ShoppingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.constant.EndPoints.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ShoppingController {
    private final ShoppingService shoppingService;
    @GetMapping("/getcartbyid")
    public ResponseEntity<ShoppingCart> getCartById(@RequestParam String profileId) {
        return ResponseEntity.ok(shoppingService.getCartById(profileId));
    }

        @PostMapping(CHECKOUT)
    //@PreAuthorize("hasRole('USER')")
    //@Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Receipt> checkout(@RequestParam String userId) {
        return ResponseEntity.ok(shoppingService.checkout(userId));
    }
}
