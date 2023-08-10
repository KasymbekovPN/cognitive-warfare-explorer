package ru.cwe.common.configuration.impl.reader;

import lombok.RequiredArgsConstructor;
import ru.cwe.common.configuration.api.reader.Reader;
import ru.cwe.common.configuration.api.search.Searcher;
import ru.cwe.common.configuration.exception.ConfFileNotExistException;
import ru.cwe.common.configuration.impl.search.DummySearcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class ByLineReader implements Reader<String[]> {
	private final Searcher searcher;

	public ByLineReader(String filePath) {
		this.searcher = new DummySearcher(filePath);
	}

	@Override
	public String[] read() {
		ArrayList<String> buffer = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(searcher.search()))){
			String line;
			while ((line = reader.readLine()) != null){
				buffer.add(line);
			}
		} catch (IOException ex){
			throw new ConfFileNotExistException(ex.getMessage());
		}

		String[] result = new String[buffer.size()];
		buffer.toArray(result);
		return result;
	}
}
