package com.kiraliza.spring.store.clothes.repository;

import com.kiraliza.spring.store.clothes.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>
{
    Optional<Product> findByShortUID(String shortUID);
    List<Product> findAllBySizes(String size);
}
