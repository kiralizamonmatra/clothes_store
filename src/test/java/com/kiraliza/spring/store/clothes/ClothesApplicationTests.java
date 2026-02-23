package com.kiraliza.spring.store.clothes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.mongodb.test.autoconfigure.AutoConfigureDataMongo;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@Import(TestcontainersConfiguration.class)
@SpringBootTest
//@DataMongoTest
@AutoConfigureDataMongo
@EnableAutoConfiguration
class ClothesApplicationTests
{
	@Test
	void contextLoads()
    {
	}
}
