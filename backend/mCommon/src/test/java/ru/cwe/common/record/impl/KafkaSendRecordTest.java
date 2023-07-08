package ru.cwe.common.record.impl;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.cwe.common.message.api.message.Message;
import ru.cwe.common.test.fakers.Fakers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class KafkaSendRecordTest {
	private static final UUID EXPECTED_KEY = Fakers.uuid_().random();
	private static final Message EXPECTED_MESSAGE = new TestMessage(Fakers.int_().random());

	@Test
	void shouldCheckKeyGetting(){
		KafkaSendRecord record = new KafkaSendRecord(EXPECTED_KEY, EXPECTED_MESSAGE);
		assertThat(record.key()).isEqualTo(EXPECTED_KEY);
	}

	@Test
	void shouldCheckMessageGetting(){
		KafkaSendRecord record = new KafkaSendRecord(EXPECTED_KEY, EXPECTED_MESSAGE);
		assertThat(record.value()).isEqualTo(EXPECTED_MESSAGE);
	}

	@Test
	void shouldCheckGetting(){
		Throwable throwable = catchThrowable(() -> {
			new KafkaSendRecord(EXPECTED_KEY, EXPECTED_MESSAGE).get(Fakers.str_().random(), String.class);
		});
		assertThat(throwable).isInstanceOf(RecordUnsupportedGetting.class);
	}

	@RequiredArgsConstructor
	@EqualsAndHashCode
	private static class TestMessage implements Message {
		private final int value;
	}
}
