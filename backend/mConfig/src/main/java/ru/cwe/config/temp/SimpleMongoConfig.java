package ru.cwe.config.temp;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
// TODO: 09.08.2023 del
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.function.Supplier;

@Configuration
public class SimpleMongoConfig {

	// TODO: 09.08.2023 del
//	@Autowired
//	private ApplicationArguments args;

	@Bean
	public MongoClient mongoClient(){

		// TODO: 09.08.2023 del
//		for (int i = 0; i <= 3; i++) {
//			boolean contains = args.containsOption("debug" + String.valueOf(i));
//			List<String> nonOptionArgs = args.getNonOptionArgs();
//			System.out.printf("%s %s\n", contains, nonOptionArgs);
//			System.out.println(args.getOptionValues("debug1"));
//
////			System.out.println(contains + " " + nonOptionArgs);
//		}

//		boolean debug = args.containsOption("debug1");
//		List<String> args1 = args.getNonOptionArgs();
//		if (debug) {
//			System.out.println(args1);
//		}

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
