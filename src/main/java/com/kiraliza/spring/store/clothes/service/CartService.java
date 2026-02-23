package com.kiraliza.spring.store.clothes.service;

import com.kiraliza.spring.store.clothes.exception.CartNotFoundException;
import com.kiraliza.spring.store.clothes.model.Cart;
import com.kiraliza.spring.store.clothes.model.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

//@Service
public interface CartService
{
    Cart getCart(String id) throws CartNotFoundException;
    Page<CartItem> getCartItems(String id, int page, int size) throws CartNotFoundException;
    Cart save(Cart cart);
}
