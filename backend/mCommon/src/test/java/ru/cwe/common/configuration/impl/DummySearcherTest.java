package ru.cwe.common.configuration.impl;

import org.junit.jupiter.api.Test;
import ru.cwe.common.configuration.exception.ConfFileNotExistException;

import java.io.File;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DummySearcherTest {
	@Test
	void shouldCheckSearch_ifFileDoesNotExist() {
		String filePath = "not exist";
		Throwable throwable = catchThrowable(() -> {
			new DummySearcher(filePath).search();
		});
		assertThat(throwable).isInstanceOf(ConfFileNotExistException.class);
	}

	@Test
	void shouldCheckSearch() {
		String pwd = Paths.get("").toAbsolutePath().toString();
		String packagePart = getClass().getPackageName().replace(".", "\\");
		String filePath = pwd + "\\src\\test\\java\\" + packagePart + "\\test.txt";
		File file = new DummySearcher(filePath).search();

		assertThat(file).isEqualTo(new File(filePath));
	}
}
