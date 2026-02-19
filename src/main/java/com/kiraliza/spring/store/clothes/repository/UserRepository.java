package com.kiraliza.spring.store.clothes.repository;

import com.kiraliza.spring.store.clothes.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>
{
    Optional<User> findByUsername(String username);
}
