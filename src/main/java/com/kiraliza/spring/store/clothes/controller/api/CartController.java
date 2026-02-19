package com.kiraliza.spring.store.clothes.controller.api;

import com.kiraliza.spring.store.clothes.exception.CartNotFoundException;
import com.kiraliza.spring.store.clothes.model.Cart;
import com.kiraliza.spring.store.clothes.model.CartItem;
import com.kiraliza.spring.store.clothes.service.CartService;
import com.kiraliza.spring.store.clothes.type.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.API + Routes.CART_ROOT)
public class CartController
{
    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public Cart getCart(@RequestParam(defaultValue = "0") String id) throws CartNotFoundException
    {
        return cartService.getCart(id);
    }

    @GetMapping("/{id}/items")
    public Page<CartItem> getItems(
        @PathVariable String id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) throws CartNotFoundException
    {
        return cartService.getCartItems(id, page, size);
    }

    @PostMapping
    public Cart addCart(@RequestBody Cart cart)
    {
        return cartService.save(cart);
    }
}
