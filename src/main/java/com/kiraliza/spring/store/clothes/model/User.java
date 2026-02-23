package com.kiraliza.spring.store.clothes.model;

import com.kiraliza.spring.store.clothes.type.Role;
import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

@Document(collection = "users")
public class User
{
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @NotBlank(message = "{user.username.not_blank}")
    private String username;
    private String password;
    private Role role;

    public ObjectId getId()
    {
        return id;
    }

    public User setId(ObjectId id)
    {
        this.id = id;
        return this;
    }

    public String getUsername()
    {
        return username;
    }

    public User setUsername(String username)
    {
        this.username = username;
        return this;
    }

    public String getPassword()
    {
        return password;
    }

    public User setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public Role getRole()
    {
        return role;
    }

    public User setRole(Role role)
    {
        this.role = role;
        return this;
    }
}
