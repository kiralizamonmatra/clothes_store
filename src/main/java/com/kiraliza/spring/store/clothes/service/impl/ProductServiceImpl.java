package com.kiraliza.spring.store.clothes.service.impl;

import com.kiraliza.spring.store.clothes.exception.ProductNotFoundException;
import com.kiraliza.spring.store.clothes.model.ClothesType;
import com.kiraliza.spring.store.clothes.model.Color;
import com.kiraliza.spring.store.clothes.model.Material;
import com.kiraliza.spring.store.clothes.model.Product;
import com.kiraliza.spring.store.clothes.repository.ClothesTypeRepository;
import com.kiraliza.spring.store.clothes.repository.ColorRepository;
import com.kiraliza.spring.store.clothes.repository.MaterialRepository;
import com.kiraliza.spring.store.clothes.repository.ProductRepository;
import com.kiraliza.spring.store.clothes.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ClothesTypeRepository clothesTypeRepository;

    public List<Product> getProducts()
    {
        return productRepository.findAll();
    }

    public Page<Product> getProducts(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Product getProduct(String id) throws ProductNotFoundException
    {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product findByShortUID(String shortUID) throws ProductNotFoundException
    {
        return productRepository.findByShortUID(shortUID).orElseThrow(() -> new ProductNotFoundException(shortUID));
    }

    public boolean isExistsByShortUID(String shortUID)
    {
        return productRepository.findByShortUID(shortUID).isPresent();
    }

    public Product save(Product product)
    {
        if (!product.getColors().isEmpty())
        {
            product.getColors().parallelStream().forEach(color -> {
                Optional<Color> existColor = colorRepository.findByNameIgnoreCase(color.getName());
                if (existColor.isEmpty())
                {
                    color.setId(colorRepository.save(color).getId());
                }
            });
        }
        if (product.getType() != null)
        {
            Optional<ClothesType> existClothesType = clothesTypeRepository.findByNameIgnoreCase(product.getType().getName());
            if (existClothesType.isEmpty())
            {
                product.getType().setId(clothesTypeRepository.save(product.getType()).getId());
            }
        }
        if (!product.getMaterials().isEmpty())
        {
            product.getMaterials().parallelStream().forEach(material -> {
                Optional<Material> existMaterial = materialRepository.findByNameIgnoreCase(material.getMaterial().getName());
                if (existMaterial.isEmpty())
                {
                    material.getMaterial().setId(materialRepository.save(material.getMaterial()).getId());
                }
                else
                {
                    material.getMaterial().setId(existMaterial.get().getId());
                }
            });
        }
        return productRepository.save(product);
    }
}
