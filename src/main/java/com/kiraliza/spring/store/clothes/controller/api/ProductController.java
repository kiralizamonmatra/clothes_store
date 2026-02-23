package com.kiraliza.spring.store.clothes.controller.api;

import com.kiraliza.spring.store.clothes.exception.ProductNotFoundException;
import com.kiraliza.spring.store.clothes.model.Product;
import com.kiraliza.spring.store.clothes.service.ProductService;
import com.kiraliza.spring.store.clothes.type.Routes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(Routes.API + Routes.PRODUCT_ROOT)
public class ProductController
{
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) throws ProductNotFoundException
    {
        Product product = productService.getProduct(id);
        return ResponseEntity
            .ok()
            .body(product);
    }

    @GetMapping("/list")
    public Page<Product> getList(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    )
    {
        return productService.getProducts(page, size);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) throws URISyntaxException
    {
        Product savedProduct = productService.save(product);
        return ResponseEntity
            .created(new URI(Routes.API + Routes.PRODUCT_ROOT + "/" + savedProduct.getId()))
            .body(product);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product)
    {
        Product savedProduct = productService.save(product);
        return ResponseEntity
            .ok()
            .body(savedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id)
    {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
