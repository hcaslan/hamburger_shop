package org.example.repository;

import org.example.entity.Ingredient;
import org.example.entity.Sauce;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SauceRepository extends MongoRepository<Sauce, String> {
}
