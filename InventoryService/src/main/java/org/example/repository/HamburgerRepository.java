package org.example.repository;

import org.example.entity.Hambuger;
import org.example.entity.Snack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HamburgerRepository extends MongoRepository<Hambuger, String> {
}
