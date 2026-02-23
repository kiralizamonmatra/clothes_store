package com.kiraliza.spring.store.clothes.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.util.List;

@Document(collection = "carts")
public class Cart
{
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private List<CartItem> items;

    public ObjectId getId()
    {
        return id;
    }

    public Cart setId(ObjectId id)
    {
        this.id = id;
        return this;
    }

    public List<CartItem> getItems()
    {
        return items;
    }

    public Cart setItems(List<CartItem> items)
    {
        this.items = items;
        return this;
    }

    public Double getAmount()
    {
        return items.stream().mapToDouble(CartItem::getPrice).sum();
    }
}
