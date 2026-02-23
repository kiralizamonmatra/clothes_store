package com.kiraliza.spring.store.clothes;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.rabbitmq.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

//@TestConfiguration(proxyBeanMethods = false)
//@EnableMongoRepositories
public class TestcontainersConfiguration
{
    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
//    @Bean
//    @ServiceConnection
//    MongoDBContainer mongoDbContainer()
//    {
//        return new MongoDBContainer(DockerImageName.parse("mongo:latest"));
//    }
//
//    @Bean
//    @ServiceConnection
//    RabbitMQContainer rabbitContainer()
//    {
//        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:latest"));
//    }
}
