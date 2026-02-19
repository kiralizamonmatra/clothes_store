package com.kiraliza.spring.store.clothes.exception;

public class AccessDeniedException extends RuntimeException
{
    public AccessDeniedException(Exception e)
    {
        super(e.getMessage());
    }

    public AccessDeniedException()
    {
        super("Access Denied");
    }
}
