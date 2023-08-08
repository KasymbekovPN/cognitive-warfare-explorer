package ru.cwe.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cwe.config.temp.Customer;
import ru.cwe.config.temp.CustomerRepo;

import static org.junit.jupiter.api.Assertions.*;

// TODO: 08.08.2023 del
@SpringBootTest
class ApplicationTest {

	@Autowired
	private CustomerRepo repo;

	@BeforeEach
	void setUp() {
		repo.deleteAll();
	}

	@Test
	void test() {
//		System.out.println("!!!: " + repo);

		Customer entity0 = new Customer("f0", "l0");
//		repo.insert(entity0);

		repo.save(entity0);
	}
}
