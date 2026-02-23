package com.kiraliza.spring.store.clothes.type;

import org.springframework.stereotype.Component;

@Component
public class Routes
{
    private String path = "";

    public static final String HOME = "/";
    public static final String API = "/api";
    public static final String ADMIN_ROOT = "/admin";
    public static final String CART_ROOT = "/cart";
    public static final String CATALOG_ROOT = "/catalog";
    public static final String PRODUCT_ROOT = "/product";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";

    public Routes()
    {
    }

    public Routes(String path)
    {
        this.path = path;
    }

    public Routes API()
    {
        this.path += API;
        return this;
    }

    public Routes add(String path)
    {
        this.path += path;
        return this;
    }

    public String build()
    {
        return path;
    }
}
