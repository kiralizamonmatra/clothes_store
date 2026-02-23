package com.kiraliza.spring.store.clothes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.webmvc.autoconfigure.error.ErrorMvcAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class ClothesApplication implements WebMvcConfigurer
{
    private final LocaleChangeInterceptor localeChangeInterceptor;

    public ClothesApplication(LocaleChangeInterceptor localeChangeInterceptor)
    {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

	public static void main(String[] args)
    {
		SpringApplication.run(ClothesApplication.class, args);
	}

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor);
    }
}
