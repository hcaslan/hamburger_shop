package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.constant.EIngredientType;
import org.example.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.example.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT + INVENTORY)
//@PreAuthorize("hasAnyAuthority('ADMIN')")
//@SecurityRequirement(name = "bearerAuth")
public class InventoryController {
    private final IngredientService ingredientService;

    @PostMapping(SAVE+INGREDIENT)
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> saveIngredient(@RequestParam String name,
                                                 @RequestParam EIngredientType type) {
        ingredientService.saveIngredient(name, type);
        return ResponseEntity.ok("Ingredient saved successfully");
    }

    @PostMapping(SAVE+SAUCE)
    public ResponseEntity<String> saveSauce(@RequestParam String name) {
        ingredientService.saveSauce(name);
        return ResponseEntity.ok("Sauce saved successfully");
    }

    @PostMapping(SAVE+SNACK)
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> saveSnack(@RequestParam String name) {
        ingredientService.saveSnack(name);
        return ResponseEntity.ok("Snack saved successfully");
    }
    @PostMapping(SAVE+DESSERT)
    public ResponseEntity<String> saveDessert(@RequestParam String name) {
        ingredientService.saveDessert(name);
        return ResponseEntity.ok("Dessert saved successfully");
    }

    @PostMapping(SAVE+DRINK)
    public ResponseEntity<String> saveDring(@RequestParam String name) {
        ingredientService.saveDrink(name);
        return ResponseEntity.ok("Drink saved successfully");
    }

    @PostMapping(SAVE+HAMBURGER)
    public ResponseEntity<String> saveHamburger(@RequestParam String name) {
        ingredientService.saveHamburger(name);
        return ResponseEntity.ok("Hamburger saved successfully");
    }
}
