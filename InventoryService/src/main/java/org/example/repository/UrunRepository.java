package org.example.repository;

import org.example.entity.Urun;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrunRepository extends MongoRepository<Urun, String> {
}

