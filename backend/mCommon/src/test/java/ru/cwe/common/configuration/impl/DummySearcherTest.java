package ru.cwe.common.configuration.impl;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DummySearcherTest {

	@Test
	void test() {
		File file = new File("");

		Path path0 = Paths.get("");
		System.out.println(path0.toAbsolutePath());
		System.out.println(path0.toFile());
		System.out.println(path0.toUri());
		System.out.println(path0.toString());

		System.out.println(file);
	}
}
