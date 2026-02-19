package com.kiraliza.spring.store.clothes.to;

import com.kiraliza.spring.store.clothes.exception.CartNotFoundException;

public class CartErrorMessage extends ErrorMessage
{
    private String id;

    public CartErrorMessage(String message)
    {
        super(message);
    }

    public CartErrorMessage(CartNotFoundException e)
    {
        super(e.getMessage());
        this.id = e.getId();
    }

    public String getId()
    {
        return id;
    }

    public CartErrorMessage setId(String id)
    {
        this.id = id;
        return this;
    }
}
