package com.kiraliza.spring.store.clothes.exception;

import com.kiraliza.spring.store.clothes.model.User;

public class UserNotFoundException extends RuntimeException
{
    private String id;
    private String username;

    public UserNotFoundException(Exception e)
    {
        super(e);
    }

    public UserNotFoundException(User user)
    {
        super(String.format("User %s[%s] not found", user.getUsername(), user.getId().toString()));

        id = user.getId().toString();
        username = user.getUsername();
    }

    public UserNotFoundException(String id)
    {
        super(String.format("User [%s] not found", id));

        this.id = id;
        this.username = null;
    }

    public UserNotFoundException(String id, String username)
    {
        super(String.format("User %s[%s] not found", username, id));

        this.id = id;
        this.username = username;
    }

    public String getId()
    {
        return id;
    }

    public UserNotFoundException setId(String id)
    {
        this.id = id;
        return this;
    }

    public String getUsername()
    {
        return username;
    }

    public UserNotFoundException setUsername(String username)
    {
        this.username = username;
        return this;
    }
}
