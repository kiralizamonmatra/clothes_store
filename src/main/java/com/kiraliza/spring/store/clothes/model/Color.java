package com.kiraliza.spring.store.clothes.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "colors")
public class Color
{
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String name;

    public Color()
    {
    }

    public Color(String name)
    {
        this.name = name;
    }

    public ObjectId getId()
    {
        return id;
    }

    public Color setId(ObjectId id)
    {
        this.id = id;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public Color setName(String name)
    {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Color color = (Color) o;
        return Objects.equals(name, color.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }
}
