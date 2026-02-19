package com.kiraliza.spring.store.clothes.to;

public class ErrorMessage
{
    private String message;

    public ErrorMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public ErrorMessage setMessage(String message)
    {
        this.message = message;
        return this;
    }
}
