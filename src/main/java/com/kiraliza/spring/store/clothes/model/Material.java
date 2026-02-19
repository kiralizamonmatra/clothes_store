package com.kiraliza.spring.store.clothes.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "materials")
public class Material
{
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String name;

    public Material()
    {
    }

    public Material(String name)
    {
        this.name = name;
    }

    public ObjectId getId()
    {
        return id;
    }

    public Material setId(ObjectId id)
    {
        this.id = id;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public Material setName(String name)
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
        Material material = (Material) o;
        return Objects.equals(name, material.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }
}
