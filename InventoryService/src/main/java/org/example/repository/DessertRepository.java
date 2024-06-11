package org.example.repository;

import org.example.entity.Dessert;
import org.example.entity.Sauce;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DessertRepository extends MongoRepository<Dessert, String> {
}
