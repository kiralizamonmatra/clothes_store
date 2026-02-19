package com.kiraliza.spring.store.clothes.service.impl;

import com.kiraliza.spring.store.clothes.exception.CartNotFoundException;
import com.kiraliza.spring.store.clothes.model.Cart;
import com.kiraliza.spring.store.clothes.model.CartItem;
import com.kiraliza.spring.store.clothes.repository.CartRepository;
import com.kiraliza.spring.store.clothes.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService
{
    @Autowired
    private CartRepository cartRepository;

    public Cart getCart(String id) throws CartNotFoundException
    {
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    }

    public Page<CartItem> getCartItems(String id, int page, int size) throws CartNotFoundException
    {
        Pageable pageable = PageRequest.of(page, size);
        Optional<Cart> cart = cartRepository.findCartWithPaginatedItems(id, page, size);
        if (cart.isPresent())
        {
            return new PageImpl<CartItem>(cart.get().getItems());
        }

        throw new CartNotFoundException(id);
    }

    public Cart save(Cart cart)
    {
        return cartRepository.save(cart);
    }
}
