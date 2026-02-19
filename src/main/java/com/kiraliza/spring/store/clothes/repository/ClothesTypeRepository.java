package com.kiraliza.spring.store.clothes.repository;

import com.kiraliza.spring.store.clothes.model.ClothesType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClothesTypeRepository extends MongoRepository<ClothesType, String>
{
    Optional<ClothesType> findByTypeIgnoreCase(String type);
}
