package ru.cwe.common.configuration.impl;

import lombok.RequiredArgsConstructor;
import ru.cwe.common.configuration.api.search.Searcher;
import ru.cwe.common.configuration.exception.ConfFileAccessDeniedException;
import ru.cwe.common.configuration.exception.ConfFileNotExistException;

import java.io.File;

@RequiredArgsConstructor
public class DummySearcher implements Searcher {
	private final String fileName;

	@Override
	public File search() {
		File file = new File(fileName);
		if (!file.exists()){
			throw new ConfFileNotExistException(fileName);
		}
		if (!file.canRead()){
			throw new ConfFileAccessDeniedException(fileName);
		}

		return file;
	}
}
