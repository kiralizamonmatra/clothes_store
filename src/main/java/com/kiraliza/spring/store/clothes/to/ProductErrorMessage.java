package com.kiraliza.spring.store.clothes.to;

import com.kiraliza.spring.store.clothes.exception.ProductNotFoundException;

public class ProductErrorMessage extends ErrorMessage
{
    private String id;
    private String name;

    public ProductErrorMessage(String message)
    {
        super(message);
    }

    public ProductErrorMessage(ProductNotFoundException e)
    {
        super(e.getMessage());
        this.id = e.getId();
        this.name = e.getName();
    }

    public String getId()
    {
        return id;
    }

    public ProductErrorMessage setId(String id)
    {
        this.id = id;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public ProductErrorMessage setName(String name)
    {
        this.name = name;
        return this;
    }
}
