package com.kiraliza.spring.store.clothes.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "products")
public class Product
{
    @Id
    private ObjectId id;
    private String name;
    @Field(name = "short_uid")
    @Indexed(unique = true)
    private String shortUID;
    private String description;
    @DocumentReference
    private ClothesType type;
    private final Set<Size> sizes = new HashSet<>();
    private final Set<ConsistMaterial> materials = new HashSet<>();
    @DocumentReference
    private final Set<Color> colors = new HashSet<>();
    private double price;

    public Product()
    {
        shortUID = String.valueOf(Instant.now().getEpochSecond() - 1734480000);
    }

    public ObjectId getId()
    {
        return id;
    }

    public Product setId(ObjectId id)
    {
        this.id = id;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public Product setName(String name)
    {
        this.name = name;
        return this;
    }

    public String getShortUID()
    {
        return shortUID;
    }

    public Product setShortUID(String shortUID)
    {
        this.shortUID = shortUID;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public Product setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public Set<Size> getSizes()
    {
        return sizes;
    }

    public Product setSizes(Set<Size> sizes)
    {
        this.sizes.clear();
        if (sizes != null)
        {
            this.sizes.addAll(sizes);
        }
        return this;
    }

    public Product addSize(Size size)
    {
        if (size != null)
        {
            this.sizes.stream().filter(s -> s.equals(size)).findFirst().ifPresentOrElse(s -> s.addQuantity(size.getQuantity()), () -> this.sizes.add(size));
        }
        return this;
    }

    public Set<ConsistMaterial> getMaterials()
    {
        return materials;
    }

    public Product setMaterials(Set<ConsistMaterial> materials)
    {
        this.materials.clear();
        if (materials != null)
        {
            this.materials.addAll(materials);
        }
        return this;
    }

    public Product addMaterial(ConsistMaterial material)
    {
        if (material != null)
        {
            this.materials.add(material);
        }
        return this;
    }

    public double getPrice()
    {
        return price;
    }

    public Product setPrice(double price)
    {
        this.price = price;
        return this;
    }

    public ClothesType getType()
    {
        return type;
    }

    public Product setType(ClothesType type)
    {
        this.type = type;
        return this;
    }

    public Set<Color> getColors()
    {
        return colors;
    }

    public Product setColors(Set<Color> colors)
    {
        this.colors.clear();
        if (colors != null)
        {
            this.colors.addAll(colors);
        }
        return this;
    }

    public Product addColor(Color color)
    {
        if (color != null)
        {
            this.colors.add(color);
        }
        return this;
    }
}
