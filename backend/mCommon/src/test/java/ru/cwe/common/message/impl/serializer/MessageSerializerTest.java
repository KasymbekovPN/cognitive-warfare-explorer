package ru.cwe.common.message.impl.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.cwe.common.message.api.message.Message;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

class MessageSerializerTest {

	@SneakyThrows
	@Test
	void shouldCheckSerialization() {
		TestMessage message = new TestMessage(123, "abc", List.of(1.1, 1.2));
		byte[] classNameBytes = message.getClass().getName().getBytes(StandardCharsets.UTF_8);
		byte[] messageBytes = new ObjectMapper().writeValueAsBytes(message);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		stream.write((classNameBytes.length / 256) - Byte.MIN_VALUE);
		stream.write((classNameBytes.length % 256) - Byte.MIN_VALUE);
		stream.write(classNameBytes);
		stream.write(messageBytes);

		@SuppressWarnings("resource")
		MessageSerializer messageSerializer = new MessageSerializer();
		byte[] bytes = messageSerializer.serialize("", message);

		Assertions.assertThat(bytes).isEqualTo(stream.toByteArray());
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@ToString
	@EqualsAndHashCode
	private static class TestMessage implements Message {
		private int intValue;
		private String strValue;
		private List<Double> list;
	}
}
