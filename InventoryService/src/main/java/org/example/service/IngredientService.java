package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.constant.EIngredientType;
import org.example.entity.Ingredient;
import org.example.repository.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public void saveIngredient(String name, EIngredientType type) {
        // todo: zaten bu isimde bir ingredient varsa ekleme.
        ingredientRepository.save(Ingredient.builder().name(name).type(type).build());
    }
}
