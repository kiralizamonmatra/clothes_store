package com.kiraliza.spring.store.clothes.service;

import com.kiraliza.spring.store.clothes.exception.ProductNotFoundException;
import com.kiraliza.spring.store.clothes.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface ProductService
{
    List<Product> getProducts();
    Page<Product> getProducts(int page, int size);
    Product getProduct(String id) throws ProductNotFoundException;
    Product findByShortUID(String shortUID) throws ProductNotFoundException;
    boolean isExistsByShortUID(String shortUID);
    Product save(Product product);
    void delete(String id);
}
