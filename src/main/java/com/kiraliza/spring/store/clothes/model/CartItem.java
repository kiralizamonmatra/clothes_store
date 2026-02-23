package com.kiraliza.spring.store.clothes.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.util.Optional;

public class CartItem
{
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @DocumentReference
    private Product product;
    private String size;
    private int quantity;

    public ObjectId getId()
    {
        return id;
    }

    public CartItem setId(ObjectId id)
    {
        this.id = id;
        return this;
    }

    public Product getProduct()
    {
        return product;
    }

    public CartItem setProduct(Product product)
    {
        this.product = product;
        return this;
    }

    public String getSize()
    {
        return size;
    }

    public CartItem setSize(String size)
    {
        this.size = size;
        return this;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public CartItem setQuantity(int quantity)
    {
        this.quantity = quantity;
        return this;
    }

    public Double getPrice()
    {
        return Optional.ofNullable(getProduct()).map(Product::getPrice).orElse(0d);
    }
}
