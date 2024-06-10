package com.kerem.repository;

import com.kerem.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {


    Optional<Token> findByCode(String activationCode);


}
