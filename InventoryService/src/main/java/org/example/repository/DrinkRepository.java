package org.example.repository;

import org.example.entity.Drink;
import org.example.entity.Sauce;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends MongoRepository<Drink, String> {
}
