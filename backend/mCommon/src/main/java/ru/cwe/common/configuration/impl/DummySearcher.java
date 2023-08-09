package ru.cwe.common.configuration.impl;

import lombok.RequiredArgsConstructor;
import ru.cwe.common.configuration.api.search.Searcher;

import java.io.File;

@RequiredArgsConstructor
public class DummySearcher implements Searcher {
	private final String fileName;

	@Override
	public File search() {
		File file = new File(fileName);
//		file.exists()
//		file.canRead()
		return file;
	}
}
