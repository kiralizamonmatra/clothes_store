package com.kiraliza.spring.store.clothes;

import com.kiraliza.spring.store.clothes.model.*;
import com.kiraliza.spring.store.clothes.service.CartService;
import com.kiraliza.spring.store.clothes.service.ProductService;
import com.kiraliza.spring.store.clothes.service.UserService;
import com.kiraliza.spring.store.clothes.type.Gender;
import com.kiraliza.spring.store.clothes.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class ApplicationSeed implements CommandLineRunner
{
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args)
    {
        if (!userService.isExistsByUsername("superadmin"))
        {
            User admin = new User()
                .setUsername("superadmin")
                .setRole(Role.ADMIN)
                .setPassword(passwordEncoder.encode("admin123"));
            userService.save(admin);

            User manager = new User()
                .setUsername("manager_1")
                .setRole(Role.MANAGER)
                .setPassword(passwordEncoder.encode("manager123"));
            userService.save(manager);
        }

        if (!productService.isExistsByShortUID("31536000"))
        {
            Product shirtRed = new Product()
                .setShortUID("31536000")
                .setName("Shirt Red and White")
                .addColor(new Color("Red"))
                .addColor(new Color("White"))
                .setSizes(new HashSet<>(Arrays.asList(new Size("40"), new Size("42", 3), new Size("44", 2))))
                .setPrice(2300d)
                .setType(new ClothesType("Shirt").setGender(Gender.MALE).setChild(true))
                .addMaterial(new ConsistMaterial().setMaterial(new Material("Cotton")));
            productService.save(shirtRed);

            Product dressBlack = new Product()
                .setShortUID("31536001")
                .setName("Dress Black")
                .setDescription("LV")
                .addColor(new Color("Black"))
                .setSizes(new HashSet<>(Arrays.asList(new Size("44"), new Size("48", 2))))
                .setPrice(3000d)
                .addColor(new Color("Black"))
                .setType(new ClothesType("Dress").setGender(Gender.FEMALE))
                .addMaterial(new ConsistMaterial().setMaterial(new Material("Cotton")).setPercent(83))
                .addMaterial(new ConsistMaterial().setMaterial(new Material("Polyester")).setPercent(17));
            productService.save(dressBlack);
        }
    }
}