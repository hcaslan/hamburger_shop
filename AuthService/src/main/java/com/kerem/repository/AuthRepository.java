package com.kerem.repository;

import com.kerem.entity.Auth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends MongoRepository<Auth, String>{
    Optional<Auth> findByEmail(String email);
}
