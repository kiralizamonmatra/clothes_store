package com.kiraliza.spring.store.clothes.config;

import com.mongodb.client.MongoClients;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
//@EnableMongoRepositories
public class MongoConfig
{
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory()
    {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(uri), database);
    }

    @Bean
    public MongoTemplate mongoTemplate()
    {
        return new MongoTemplate(mongoDatabaseFactory());
    }

    @Bean
    public ValidatingEntityCallback validatingEntityCallback(Validator validator)
    {
        return new ValidatingEntityCallback(validator);
    }
}
