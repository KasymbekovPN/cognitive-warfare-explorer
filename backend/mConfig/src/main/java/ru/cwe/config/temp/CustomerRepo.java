package ru.cwe.config.temp;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// TODO: 08.08.2023 del
public interface CustomerRepo extends MongoRepository<Customer, String> {
	Customer findByFirstName(String firstName);
	List<Customer> findByLastName(String lastName);
}
