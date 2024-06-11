package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.constant.EIngredientType;
import org.example.entity.*;
import org.example.repository.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final SauceRepository sauceRepository;
    private final DessertRepository dessertRepository;
    private final SnackRepository snackRepository;
    private final DrinkRepository drinkRepository;
    private final HamburgerRepository hamburgerRepository;

    public void saveIngredient(String name, EIngredientType type) {
        // todo: zaten bu isimde bir ingredient varsa ekleme.
        ingredientRepository.save(Ingredient.builder().name(name).type(type).build());
    }

    public void saveSauce(String name) {
        sauceRepository.save(Sauce.builder().name(name).build());
    }

    public void saveSnack(String name) {
        snackRepository.save(Snack.builder().name(name).build());
    }

    public void saveDessert(String name) {
        dessertRepository.save(Dessert.builder().name(name).build());
    }

    public void saveDrink(String name) {
        drinkRepository.save(Drink.builder().name(name).build());
    }

    public void saveHamburger(String name) {
        hamburgerRepository.save(Hambuger.builder().name(name).build());
    }
}
