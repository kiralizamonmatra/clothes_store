package com.kiraliza.spring.store.clothes.to;

import com.kiraliza.spring.store.clothes.exception.UserNotFoundException;

public class UserErrorMessage extends ErrorMessage
{
    private String id;
    private String username;

    public UserErrorMessage(String message)
    {
        super(message);
    }

    public UserErrorMessage(UserNotFoundException e)
    {
        super(e.getMessage());
        this.id = e.getId();
        this.username = e.getUsername();
    }

    public String getId()
    {
        return id;
    }

    public UserErrorMessage setId(String id)
    {
        this.id = id;
        return this;
    }

    public String getUsername()
    {
        return username;
    }

    public UserErrorMessage setUsername(String username)
    {
        this.username = username;
        return this;
    }
}
