package com.kiraliza.spring.store.clothes.model;

import org.springframework.data.mongodb.core.mapping.DocumentReference;

public class ConsistMaterial
{
    @DocumentReference
    private Material material;
    private int percent = 100;

    public ConsistMaterial()
    {
    }

    public ConsistMaterial(Material material)
    {
        this.material = material;
    }

    public Material getMaterial()
    {
        return material;
    }

    public ConsistMaterial setMaterial(Material material)
    {
        this.material = material;
        return this;
    }

    public int getPercent()
    {
        return percent;
    }

    public ConsistMaterial setPercent(int percent)
    {
        this.percent = percent;
        return this;
    }
}
