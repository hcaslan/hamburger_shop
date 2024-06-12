package org.example.repository;

import org.example.entity.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, String> {
    ShoppingCart findByUserId(String userId);
}
