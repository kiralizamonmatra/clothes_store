package com.kiraliza.spring.store.clothes.controller.api;

import com.kiraliza.spring.store.clothes.exception.ProductNotFoundException;
import com.kiraliza.spring.store.clothes.model.Product;
import com.kiraliza.spring.store.clothes.service.ProductService;
import com.kiraliza.spring.store.clothes.type.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.API + Routes.PRODUCT_ROOT)
public class ProductController
{
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@RequestParam(defaultValue = "0") String id) throws ProductNotFoundException
    {
        return productService.getProduct(id);
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
    public Product addProduct(@RequestBody Product product)
    {
        return productService.save(product);
    }
}
