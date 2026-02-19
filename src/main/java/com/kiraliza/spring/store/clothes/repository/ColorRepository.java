package com.kiraliza.spring.store.clothes.repository;

import com.kiraliza.spring.store.clothes.model.Color;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends MongoRepository<Color, String>
{
    Optional<Color> findByNameIgnoreCase(String name);
}
