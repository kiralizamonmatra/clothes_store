package com.kiraliza.spring.store.clothes.model;

import java.util.Objects;

public class Size
{
    private String size;
    private int quantity;

    public Size()
    {
    }

    public Size(String size)
    {
        this.size = size;
        this.quantity = 1;
    }

    public Size(String size, int quantity)
    {
        this.size = size;
        this.quantity = quantity;
    }

    public String getSize()
    {
        return size;
    }

    public Size setSize(String size)
    {
        this.size = size;
        return this;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public Size setQuantity(int quantity)
    {
        this.quantity = quantity;
        return this;
    }

    public Size addQuantity(int quantity)
    {
        this.quantity += quantity;
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
        Size size1 = (Size) o;
        return Objects.equals(size, size1.size);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(size);
    }
}
