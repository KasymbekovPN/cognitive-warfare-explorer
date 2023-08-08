package ru.cwe.config.temp;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class SimpleMongoConfig {

	@Bean
	public MongoClient mongoClient(){
		// TODO: 08.08.2023 !!!
		ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
		MongoClientSettings settings = MongoClientSettings.builder()
			.applyConnectionString(connectionString)
			.build();

		return MongoClients.create(settings);
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), "test");
	}
}
