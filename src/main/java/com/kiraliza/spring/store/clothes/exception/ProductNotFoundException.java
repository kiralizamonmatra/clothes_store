package com.kiraliza.spring.store.clothes.exception;

import com.kiraliza.spring.store.clothes.model.Product;

public class ProductNotFoundException extends RuntimeException
{
    private String id;
    private String name;

    public ProductNotFoundException(Exception e)
    {
        super(e);
    }

    public ProductNotFoundException(Product product)
    {
        super(String.format("Product %s[%s] not found", product.getName(), product.getId().toString()));

        id = product.getId().toString();
        name = product.getName();
    }

    public ProductNotFoundException(String id)
    {
        super(String.format("Product [%s] not found", id));

        this.id = id;
        this.name = null;
    }

    public ProductNotFoundException(String id, String name)
    {
        super(String.format("Product %s[%s] not found", name, id));

        this.id = id;
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public ProductNotFoundException setId(String id)
    {
        this.id = id;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public ProductNotFoundException setName(String name)
    {
        this.name = name;
        return this;
    }
}
