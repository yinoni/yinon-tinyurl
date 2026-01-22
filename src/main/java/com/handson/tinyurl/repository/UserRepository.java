package com.handson.tinyurl.repository;

import com.handson.tinyurl.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findFirstByUsername(String username);
}
