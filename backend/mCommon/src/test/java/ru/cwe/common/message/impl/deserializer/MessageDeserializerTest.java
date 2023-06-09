package ru.cwe.common.message.impl.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.commons.lang3.SerializationException;
import org.junit.jupiter.api.Test;
import ru.cwe.common.message.api.message.Message;
import ru.cwe.common.test.fakers.Fakers;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class MessageDeserializerTest {

	@Test
	void shouldCheckDeserialization_ifDataNull() {
		Throwable throwable = catchThrowable(() -> {
			new MessageDeserializer().deserialize(Fakers.str_().random(), null);
		});

		assertThat(throwable)
			.isInstanceOf(SerializationException.class)
			.hasMessage(MessageDeserializer.ExceptionMessage.IS_NULL.getValue());
	}

	@Test
	void shouldCheckDeserialization_ifDataDoesNotContainClassNameLen() {
		byte[] bytes = Fakers.byte_().array(1);
		Throwable throwable = catchThrowable(() -> {
			new MessageDeserializer().deserialize(Fakers.str_().random(), bytes);
		});

		assertThat(throwable)
			.isInstanceOf(SerializationException.class)
			.hasMessage(MessageDeserializer.ExceptionMessage.NOT_CONTAIN_NAME_LEN.getValue());
	}

	@Test
	void shouldCheckDeserialization_ifDataDoesNotContainClassName() {
		byte[] bytes = Fakers.byte_().array(5);
		Throwable throwable = catchThrowable(() -> {
			new MessageDeserializer().deserialize(Fakers.str_().random(), bytes);
		});

		assertThat(throwable)
			.isInstanceOf(SerializationException.class)
			.hasMessage(MessageDeserializer.ExceptionMessage.NOT_CONTAIN_CLASS_NAME.getValue());
	}

	@SneakyThrows
	@Test
	void shouldCheckDeserialization_ifBadClassName() {
		String className = this.getClass().getName();
		String badClassName = className.substring(0, className.length() - 2);

		ByteArrayOutputStream stream = createStream(badClassName.length());
		stream.write(badClassName.getBytes(StandardCharsets.UTF_8));

		Throwable throwable = catchThrowable(() -> {
			new MessageDeserializer().deserialize(Fakers.str_().random(), stream.toByteArray());
		});

		assertThat(throwable).isInstanceOf(SerializationException.class);

		String message = throwable.getMessage();
		String prefix = MessageDeserializer.ExceptionMessage.BAD_CLASS_NAME.getValue();
		assertThat(message.substring(0, prefix.length())).isEqualTo(prefix);
		assertThat(message.substring(prefix.length())).isEqualTo(badClassName);
	}

	@SneakyThrows
	@Test
	void shouldCheckDeserialization_ifBadClass() {
		String className = BadClass.class.getName();

		ByteArrayOutputStream stream = createStream(className.length());
		stream.write(className.getBytes(StandardCharsets.UTF_8));

		Throwable throwable = catchThrowable(() -> {
			new MessageDeserializer().deserialize(Fakers.str_().random(), stream.toByteArray());
		});

		assertThat(throwable).isInstanceOf(SerializationException.class);

		String message = throwable.getMessage();
		String prefix = MessageDeserializer.ExceptionMessage.BAD_CLASS.getValue();
		assertThat(message.substring(0, prefix.length())).isEqualTo(prefix);
		assertThat(message.substring(prefix.length())).isEqualTo(className);
	}

	@SneakyThrows
	@Test
	void shouldCheckDeserialization_ifDataDoesNotContainInstanceData() {
		String className = TestMessage.class.getName();

		ByteArrayOutputStream stream = createStream(className.length());
		stream.write(className.getBytes(StandardCharsets.UTF_8));

		Throwable throwable = catchThrowable(() -> {
			new MessageDeserializer().deserialize(Fakers.str_().random(), stream.toByteArray());
		});

		assertThat(throwable)
			.isInstanceOf(SerializationException.class)
			.hasMessage(MessageDeserializer.ExceptionMessage.EMPTY_INSTANCE_DATA.getValue());
	}

	@SneakyThrows
	@Test
	void shouldCheckDeserialization_ifFailDataDeserialization() {
		String className = BadTestMessage.class.getName();

		TestMessage testMessage = new TestMessage(
			Fakers.int_().random(),
			Fakers.str_().random(),
			Fakers.double_().list(2)
		);

		ByteArrayOutputStream stream = createStream(className.length());
		stream.write(className.getBytes(StandardCharsets.UTF_8));
		stream.write(new ObjectMapper().writeValueAsBytes(testMessage));

		Throwable throwable = catchThrowable(() -> {
			new MessageDeserializer().deserialize(Fakers.str_().random(), stream.toByteArray());
		});

		assertThat(throwable)
			.isInstanceOf(SerializationException.class);
	}

	@SneakyThrows
	@Test
	void shouldCheckDeserialization() {
		TestMessage expectedMessage = new TestMessage(
			Fakers.int_().random(),
			Fakers.str_().random(),
			Fakers.double_().list(2)
		);

		byte[] classNameBytes = expectedMessage.getClass().getName().getBytes(StandardCharsets.UTF_8);
		ByteArrayOutputStream stream = createStream(classNameBytes.length);
		stream.write(classNameBytes);
		stream.write(new ObjectMapper().writeValueAsBytes(expectedMessage));

		@SuppressWarnings("resource")
		Message message = new MessageDeserializer().deserialize(Fakers.str_().random(), stream.toByteArray());

		assertThat(message).isEqualTo(expectedMessage);
	}

	private static ByteArrayOutputStream createStream(int len){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		stream.write((len / 256) - Byte.MIN_VALUE);
		stream.write((len % 256) - Byte.MIN_VALUE);

		return stream;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@EqualsAndHashCode
	public static class TestMessage implements Message {
		private int intValue;
		private String strValue;
		private List<Double> list;
	}

	@RequiredArgsConstructor
	@Getter
	@Setter
	@EqualsAndHashCode
	public static class BadTestMessage implements Message {
		private final float value;
	}

	public static class BadClass {}
}
