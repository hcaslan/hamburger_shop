package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.constant.EIngredientType;
import org.example.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.example.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT + INGREDIENT)
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping(SAVE)
    public ResponseEntity<String> saveIngredient(@RequestParam String name,
                                                 @RequestParam EIngredientType type) {
        ingredientService.saveIngredient(name, type);
        return ResponseEntity.ok("Ingredient saved successfully");
    }
}
