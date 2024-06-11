package org.example.repository;

import org.example.entity.Sauce;
import org.example.entity.Snack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnackRepository extends MongoRepository<Snack, String> {
}
