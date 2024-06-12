package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.ShoppingCart;
import org.example.service.ShoppingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ShoppingController {
    private final ShoppingService shoppingService;
    @GetMapping("/getcartbyid")
    public ResponseEntity<ShoppingCart> getCartById(@RequestParam String profileId) {
        return ResponseEntity.ok(shoppingService.getCartById(profileId));
    }
}
