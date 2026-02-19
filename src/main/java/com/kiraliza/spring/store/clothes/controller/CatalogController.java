package com.kiraliza.spring.store.clothes.controller;

import com.kiraliza.spring.store.clothes.helper.LogHelper;
import com.kiraliza.spring.store.clothes.model.Product;
import com.kiraliza.spring.store.clothes.service.ProductService;
import com.kiraliza.spring.store.clothes.type.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
@RequestMapping(Routes.CATALOG_ROOT)
public class CatalogController
{
    @Autowired
    private Routes routes;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public String catalog(
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Authentication authentication
    )
    {
        LogHelper.info("==CATALOG:");
        if (authentication != null)
        {
            LogHelper.info("==name:" + authentication.getName());
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            authorities.forEach(a -> LogHelper.info("ROLE:" + a.getAuthority()));
        }

        Page<Product> productPage = productService.getProducts(page, size);
        productPage.forEach(p -> LogHelper.info(objectMapper.writeValueAsString(p)));

        model.addAttribute("productPage", productPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0)
        {
            List<Integer> pageNumbers = IntStream
                .rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "catalog";
    }
}
