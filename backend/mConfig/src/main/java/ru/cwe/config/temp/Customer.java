package ru.cwe.config.temp;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


// TODO: 08.08.2023 del
@Document(collection = "test_con")
@Data
public class Customer {
	@Id
//	private Long id;
//	private String id;
	private ObjectId id;

	@Indexed
	private String firstName;
	private String lastName;

	public Customer() {
	}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
