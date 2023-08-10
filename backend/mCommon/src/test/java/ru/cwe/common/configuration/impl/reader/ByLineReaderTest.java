package ru.cwe.common.configuration.impl.reader;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.cwe.common.configuration.api.search.Searcher;
import ru.cwe.common.configuration.exception.ConfFileNotExistException;

import java.io.*;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ByLineReaderTest {

	@Test
	void shouldCheckReading_ifFileNotExist() {
		File file = getFile("not-exist.txt");
		Throwable throwable = catchThrowable(() -> {
			new ByLineReader(new TestSearcher(file)).read();
		});

		assertThat(throwable).isInstanceOf(ConfFileNotExistException.class);
	}

	@Test
	void shouldCheckReading() {
		File file = getFile("test.txt");
		String[] result = new ByLineReader(new TestSearcher(file)).read();

		String[] expected = {"arg0 = 123", "arg1 = hello world"};
		assertThat(result).isEqualTo(expected);
	}

	private File getFile(String fileName){
		String pwd = Paths.get("").toAbsolutePath().toString();
		String packagePart = getClass().getPackageName().replace(".", "\\");
		String filePath = pwd + "\\src\\test\\java\\" + packagePart + "\\" + fileName;

		return new File(filePath);
	}

	@RequiredArgsConstructor
	private static class TestSearcher implements Searcher{
		private final File file;

		@Override
		public File search() {
			return file;
		}
	}
}
