package com.kiraliza.spring.store.clothes.repository;

import com.kiraliza.spring.store.clothes.model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends MongoRepository<Material, String>
{
    Optional<Material> findByNameIgnoreCase(String name);
}
