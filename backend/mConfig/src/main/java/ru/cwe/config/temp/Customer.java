package ru.cwe.config.temp;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class Customer {
	@Id
	private Long id;

	private String firstName;
	private String secondName;

	public Customer() {
	}

	public Customer(String firstName, String secondName) {
		this.firstName = firstName;
		this.secondName = secondName;
	}
}
