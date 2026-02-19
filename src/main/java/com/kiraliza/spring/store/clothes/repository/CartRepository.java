package com.kiraliza.spring.store.clothes.repository;

import com.kiraliza.spring.store.clothes.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String>
{
    @Query(value = "{ 'id': ?0 }", fields = "{ 'items': { $slice: [?1, ?2] }, 'quantity': 1, '_id': 0 }")
    Optional<Cart> findCartWithPaginatedItems(String id, int skip, int limit);
}
