package com.kiraliza.spring.store.clothes.exception;

import com.kiraliza.spring.store.clothes.model.Cart;

public class CartNotFoundException extends RuntimeException
{
    private String id;

    public CartNotFoundException(Exception e)
    {
        super(e.getMessage());
    }

    public CartNotFoundException(Cart cart)
    {
        super(String.format("Cart [%s] not found", cart.getId().toString()));

        id = cart.getId().toString();
    }

    public CartNotFoundException(String id)
    {
        super(String.format("Cart [%s] not found", id));

        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public CartNotFoundException setId(String id)
    {
        this.id = id;
        return this;
    }
}
