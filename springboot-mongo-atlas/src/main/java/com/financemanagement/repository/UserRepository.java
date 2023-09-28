package com.financemanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.financemanagement.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
