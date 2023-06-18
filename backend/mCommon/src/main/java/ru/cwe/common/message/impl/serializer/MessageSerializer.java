package ru.cwe.common.message.impl.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import ru.cwe.common.message.api.message.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class MessageSerializer implements Serializer<Message> {

	@Override
	public byte[] serialize(String topic, Message data) {
		try {
			byte[] classNameBytes = data.getClass().getName().getBytes(StandardCharsets.UTF_8);
			byte[] messageBytes = new ObjectMapper().writeValueAsBytes(data);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			stream.write((classNameBytes.length / 256) + Byte.MIN_VALUE);
			stream.write((classNameBytes.length % 256) + Byte.MIN_VALUE);
			stream.write(classNameBytes);
			stream.write(messageBytes);

			return stream.toByteArray();
		} catch (IOException e) {
			throw new SerializationException(e.getClass().getSimpleName() + " : " + e.getMessage());
		}
	}
}
