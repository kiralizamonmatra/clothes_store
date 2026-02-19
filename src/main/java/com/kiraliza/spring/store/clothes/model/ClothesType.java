package com.kiraliza.spring.store.clothes.model;

import com.kiraliza.spring.store.clothes.type.Gender;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "clothes_types")
public class ClothesType
{
    @Id
    private ObjectId id;
    private String name;
    private Gender gender = Gender.ANY;
    private boolean child;

    public ClothesType()
    {
    }

    public ClothesType(String name)
    {
        this.name = name;
    }

    public ObjectId getId()
    {
        return id;
    }

    public ClothesType setId(ObjectId id)
    {
        this.id = id;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public ClothesType setName(String name)
    {
        this.name = name;
        return this;
    }

    public Gender getGender()
    {
        return gender;
    }

    public ClothesType setGender(Gender gender)
    {
        this.gender = gender;
        return this;
    }

    public boolean isChild()
    {
        return child;
    }

    public ClothesType setChild(boolean child)
    {
        this.child = child;
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
        ClothesType that = (ClothesType) o;
        return child == that.child && Objects.equals(name, that.name) && gender == that.gender;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, gender, child);
    }
}
