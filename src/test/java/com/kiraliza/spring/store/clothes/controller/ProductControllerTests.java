package com.kiraliza.spring.store.clothes.controller;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kiraliza.spring.store.clothes.config.SecurityConfig;
import com.kiraliza.spring.store.clothes.controller.api.ProductController;
import com.kiraliza.spring.store.clothes.exception.ProductNotFoundException;
import com.kiraliza.spring.store.clothes.model.*;
import com.kiraliza.spring.store.clothes.service.ProductService;
import com.kiraliza.spring.store.clothes.type.Gender;
import com.kiraliza.spring.store.clothes.type.Role;
import com.kiraliza.spring.store.clothes.type.Routes;
import org.bson.types.ObjectId;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.*;
import org.hamcrest.core.IsIterableContaining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
//@EnableMongoRepositories
//@WithMockUser
//@DataMongoTest
//@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
public class ProductControllerTests
{
//    @Autowired
//    private RestTestClient restTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductController productController;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void contextLoads() throws Exception
    {
        assertThat(productController).isNotNull();
    }

//    @BeforeEach
//    public void setup()
//    {
//        this.mvc = MockMvcBuilders
//            .webAppContextSetup(webApplicationContext)
//            .apply(SecurityMockMvcConfigurers.springSecurity())
//            .build();
//    }

    @Test
    void getProductById() throws Exception
    {
        ObjectId id = new ObjectId();
        Product product = new Product()
            .setId(id)
            .setShortUID("31536000")
            .setName("Shirt Red and White")
            .addColor(new Color("Red"))
            .addColor(new Color("White"))
            .setSizes(new HashSet<>(Arrays.asList(new Size("40"), new Size("42", 3), new Size("44", 2))))
            .setPrice(2300d)
            .setType(new ClothesType("Shirt").setGender(Gender.MALE).setChild(true))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Cotton")))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Viscose")).setPercent(80));

        Mockito.when(productService.getProduct(id.toString())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders
                .get(Routes.API + Routes.PRODUCT_ROOT + "/{id}", product.getId())
//                .with(SecurityMockMvcRequestPostProcessors.user("manager_1").password("manager123").roles("MANAGER"))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(product.getId().toString()))
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.short_uid").value(product.getShortUID()))
            .andExpect(jsonPath("$.colors", IsCollectionWithSize.hasSize(product.getColors().size())))
            .andExpect(jsonPath("$.colors[*]", IsIterableContaining.hasItems(
                IsMapContaining.hasEntry("name", "White"),
                IsMapContaining.hasEntry("name", "Red")
            )))
            .andExpect(jsonPath("$.sizes", IsCollectionWithSize.hasSize(product.getSizes().size())))
            .andExpect(jsonPath("$.sizes[?(@.size == '40' && @.quantity == 1)]").exists())
            .andExpect(jsonPath("$.sizes[?(@.size == '42' && @.quantity == 3)]").exists())
            .andExpect(jsonPath("$.sizes[?(@.size == '44' && @.quantity == 2)]").exists())
            .andExpect(jsonPath("$.price").value(product.getPrice()))
            .andExpect(jsonPath("$.type.name").value(product.getType().getName()))
            .andExpect(jsonPath("$.type.gender").value(product.getType().getGender().name()))
            .andExpect(jsonPath("$.materials", IsCollectionWithSize.hasSize(product.getMaterials().size())))
            .andExpect(jsonPath("$.materials[?(@.material.name == 'Cotton' && @.percent == 100)]").exists())
            .andExpect(jsonPath("$.materials[?(@.material.name == 'Viscose' && @.percent == 80)]").exists())
        ;
    }

    @Test
    void getProducts() throws Exception
    {
        Product product1 = new Product()
            .setId(new ObjectId())
            .setShortUID("31536000")
            .setName("Shirt Red and White")
            .addColor(new Color("Red"))
            .addColor(new Color("White"))
            .setSizes(new HashSet<>(Arrays.asList(new Size("40"), new Size("42", 3), new Size("44", 2))))
            .setPrice(2300d)
            .setType(new ClothesType("Shirt").setGender(Gender.MALE).setChild(true))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Cotton")))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Viscose")).setPercent(80));

        Product product2 = new Product()
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

        Product product3 = new Product()
            .setShortUID("31536002")
            .setName("Coat Yellow")
            .setDescription("LV")
            .setSizes(new HashSet<>(Arrays.asList(new Size("44"), new Size("48", 2))))
            .setPrice(3000d)
            .addColor(new Color("Yellow"))
            .setType(new ClothesType("Dress").setGender(Gender.FEMALE))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Cotton")).setPercent(83))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Polyester")).setPercent(17));

        Page<Product> pagination = new PageImpl<>(Arrays.asList(product1, product2, product3), PageRequest.of(0, 3), 3);
        Mockito
            .when(productService.getProducts(0,10))
            .thenReturn(pagination);

        mvc.perform(MockMvcRequestBuilders
                .get(Routes.API + Routes.PRODUCT_ROOT + "/list")
                .param("page", "0")
                .param("size", "10")
//                .with(SecurityMockMvcRequestPostProcessors.user("manager_1").password("manager123").roles("MANAGER"))
                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(pagination))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", IsCollectionWithSize.hasSize(3)))
            .andExpect(jsonPath("$.empty").value(false))
            .andExpect(jsonPath("$.first").value(true))
            .andExpect(jsonPath("$.last").value(true))
            .andExpect(jsonPath("$.number").value(0))
            .andExpect(jsonPath("$.numberOfElements").value(3))
            .andExpect(jsonPath("$.pageable.offset").value(0))
            .andExpect(jsonPath("$.pageable.pageNumber").value(0))
            .andExpect(jsonPath("$.pageable.pageSize").value(3))
            .andExpect(jsonPath("$.pageable.paged").value(true))
            .andExpect(jsonPath("$.pageable.sort.empty").value(true))
            .andExpect(jsonPath("$.pageable.sort.sorted").value(false))
            .andExpect(jsonPath("$.pageable.sort.unsorted").value(true))
            .andExpect(jsonPath("$.pageable.unpaged").value(false))
            .andExpect(jsonPath("$.size").value(3))
            .andExpect(jsonPath("$.sort.empty").value(true))
            .andExpect(jsonPath("$.sort.sorted").value(false))
            .andExpect(jsonPath("$.sort.unsorted").value(true))
            .andExpect(jsonPath("$.totalElements").value(3))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.content[0].id").value(product1.getId().toString()))
            .andExpect(jsonPath("$.content[0].name").value(product1.getName()))
            .andExpect(jsonPath("$.content[0].description").value(product1.getDescription()))
            .andExpect(jsonPath("$.content[0].short_uid").value(product1.getShortUID()))
            .andExpect(jsonPath("$.content[0].colors", IsCollectionWithSize.hasSize(product1.getColors().size())))
            .andExpect(jsonPath("$.content[0].colors[*]", IsIterableContaining.hasItems(
                IsMapContaining.hasEntry("name", "White"),
                IsMapContaining.hasEntry("name", "Red")
            )))
            .andExpect(jsonPath("$.content[0].sizes", IsCollectionWithSize.hasSize(product1.getSizes().size())))
            .andExpect(jsonPath("$.content[0].sizes[?(@.size == '40' && @.quantity == 1)]").exists())
            .andExpect(jsonPath("$.content[0].sizes[?(@.size == '42' && @.quantity == 3)]").exists())
            .andExpect(jsonPath("$.content[0].sizes[?(@.size == '44' && @.quantity == 2)]").exists())
            .andExpect(jsonPath("$.content[0].price").value(product1.getPrice()))
            .andExpect(jsonPath("$.content[0].type.name").value(product1.getType().getName()))
            .andExpect(jsonPath("$.content[0].type.gender").value(product1.getType().getGender().name()))
            .andExpect(jsonPath("$.content[0].materials", IsCollectionWithSize.hasSize(product1.getMaterials().size())))
            .andExpect(jsonPath("$.content[0].materials[?(@.material.name == 'Cotton' && @.percent == 100)]").exists())
            .andExpect(jsonPath("$.content[0].materials[?(@.material.name == 'Viscose' && @.percent == 80)]").exists())
        ;
    }

    @Test
    void getProductByIdNotFound() throws Exception
    {
        ObjectId id = new ObjectId();
        Mockito.when(productService.getProduct(id.toString())).thenThrow(new ProductNotFoundException(id.toString()));

        mvc.perform(MockMvcRequestBuilders
                .get(Routes.API + Routes.PRODUCT_ROOT + "/{id}", id)
//                .with(SecurityMockMvcRequestPostProcessors.user("manager_1").password("manager123").roles("MANAGER"))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.message").value("Product [" + id + "] not found"))
            .andExpect(jsonPath("$.name").value(CoreMatchers.nullValue()))
        ;
    }

    @Test
    void addProductNotAuthorized() throws Exception
    {
        ObjectId id = new ObjectId();
        Mockito.when(productService.getProduct(id.toString())).thenReturn(new Product());

        mvc.perform(MockMvcRequestBuilders
                .post(Routes.API + Routes.PRODUCT_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Product()))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isForbidden())
        ;
    }

    @Test
//    @WithMockUser(username = "manager_1", password = "manager123", authorities = {"MANAGER"})
    @WithUserDetails(value = "manager_1")
    void addProduct() throws Exception
    {
        Product product = new Product()
            .setShortUID("31536000")
            .setName("Shirt Red and White")
            .addColor(new Color("Red"))
            .addColor(new Color("White"))
            .setSizes(new HashSet<>(Arrays.asList(new Size("40"), new Size("42", 3), new Size("44", 2))))
            .setPrice(2300d)
            .setType(new ClothesType("Shirt").setGender(Gender.MALE).setChild(true))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Cotton")))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Viscose")).setPercent(80));

        Mockito.when(productService.save(Mockito.any(Product.class))).thenReturn(product);

//        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(Role.MANAGER.name()));
//        Authentication customAuth = new UsernamePasswordAuthenticationToken(
//            "manager_1", "manager123", authorities
//        );

        mvc.perform(MockMvcRequestBuilders
                .post(Routes.API + Routes.PRODUCT_ROOT)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
//                .with(SecurityMockMvcRequestPostProcessors.authentication(customAuth))
//                .with(SecurityMockMvcRequestPostProcessors.user("manager_1").password("manager123").authorities(List.of(new SimpleGrantedAuthority("MANAGER"))))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.short_uid").value(product.getShortUID()))
            .andExpect(jsonPath("$.colors", IsCollectionWithSize.hasSize(product.getColors().size())))
            .andExpect(jsonPath("$.colors[*]", IsIterableContaining.hasItems(
                IsMapContaining.hasEntry("name", "White"),
                IsMapContaining.hasEntry("name", "Red")
            )))
            .andExpect(jsonPath("$.sizes", IsCollectionWithSize.hasSize(product.getSizes().size())))
            .andExpect(jsonPath("$.sizes[?(@.size == '40' && @.quantity == 1)]").exists())
            .andExpect(jsonPath("$.sizes[?(@.size == '42' && @.quantity == 3)]").exists())
            .andExpect(jsonPath("$.sizes[?(@.size == '44' && @.quantity == 2)]").exists())
            .andExpect(jsonPath("$.price").value(product.getPrice()))
            .andExpect(jsonPath("$.type.name").value(product.getType().getName()))
            .andExpect(jsonPath("$.type.gender").value(product.getType().getGender().name()))
            .andExpect(jsonPath("$.materials", IsCollectionWithSize.hasSize(product.getMaterials().size())))
            .andExpect(jsonPath("$.materials[?(@.material.name == 'Cotton' && @.percent == 100)]").exists())
            .andExpect(jsonPath("$.materials[?(@.material.name == 'Viscose' && @.percent == 80)]").exists())
        ;
    }

    @Test
    @WithUserDetails(value = "manager_1")
    void addProductWithInvalidData() throws Exception
    {
        Product product = new Product();

        Mockito.when(productService.save(Mockito.any(Product.class))).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders
                .post(Routes.API + Routes.PRODUCT_ROOT)
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.sizes").value("{product.size.not_empty}"))
            .andExpect(jsonPath("$.price").value("must be greater than or equal to 1"))
            .andExpect(jsonPath("$.name").value("{product.name.not_blank}"))
            .andExpect(jsonPath("$.type").value("must not be null"))
        ;
    }

    @Test
    @WithUserDetails(value = "manager_1")
    void updateProduct() throws Exception
    {
        Product product = new Product()
            .setShortUID("31536000")
            .setName("Shirt Red and White")
            .addColor(new Color("Red"))
            .addColor(new Color("White"))
            .setSizes(new HashSet<>(Arrays.asList(new Size("40"), new Size("42", 3), new Size("44", 2))))
            .setPrice(2300d)
            .setType(new ClothesType("Shirt").setGender(Gender.MALE).setChild(true))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Cotton")))
            .addMaterial(new ConsistMaterial().setMaterial(new Material("Viscose")).setPercent(80));

        Mockito.when(productService.save(Mockito.any(Product.class))).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders
                    .put(Routes.API + Routes.PRODUCT_ROOT)
                    .content(objectMapper.writeValueAsString(product))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.short_uid").value(product.getShortUID()))
            .andExpect(jsonPath("$.colors", IsCollectionWithSize.hasSize(product.getColors().size())))
            .andExpect(jsonPath("$.colors[*]", IsIterableContaining.hasItems(
                IsMapContaining.hasEntry("name", "White"),
                IsMapContaining.hasEntry("name", "Red")
            )))
            .andExpect(jsonPath("$.sizes", IsCollectionWithSize.hasSize(product.getSizes().size())))
            .andExpect(jsonPath("$.sizes[?(@.size == '40' && @.quantity == 1)]").exists())
            .andExpect(jsonPath("$.sizes[?(@.size == '42' && @.quantity == 3)]").exists())
            .andExpect(jsonPath("$.sizes[?(@.size == '44' && @.quantity == 2)]").exists())
            .andExpect(jsonPath("$.price").value(product.getPrice()))
            .andExpect(jsonPath("$.type.name").value(product.getType().getName()))
            .andExpect(jsonPath("$.type.gender").value(product.getType().getGender().name()))
            .andExpect(jsonPath("$.materials", IsCollectionWithSize.hasSize(product.getMaterials().size())))
            .andExpect(jsonPath("$.materials[?(@.material.name == 'Cotton' && @.percent == 100)]").exists())
            .andExpect(jsonPath("$.materials[?(@.material.name == 'Viscose' && @.percent == 80)]").exists())
        ;
    }

    @Test
    @WithUserDetails("manager_1")
    public void deleteProduct() throws Exception
    {
        ObjectId id = new ObjectId();

        Mockito.doNothing().when(productService).delete(id.toString());

        mvc.perform(MockMvcRequestBuilders.delete(Routes.API + Routes.PRODUCT_ROOT + "/{id}", id.toString())
                .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
            .andExpect(status().isOk());
    }
}












