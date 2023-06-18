package ru.cwe.common.message.impl.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import ru.cwe.common.message.api.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class MessageDeserializer implements Deserializer<Message> {

	@Override
	public Message deserialize(String topic, byte[] data) {
		if (data == null){
			throw new SerializationException(ExceptionMessage.IS_NULL.getValue());
		}
		int classNameLen = computeClassNameLen(data);
		String className = computeClassName(classNameLen, data);
		Class<?> clazz = loadClass(className);
		String instanceData = computeInstanceData(2 + classNameLen, data);

		try {
			return (Message) new ObjectMapper().readValue(instanceData, clazz);
		} catch (JsonProcessingException e) {
			throw new SerializationException(ExceptionMessage.FAIL_DESERIALIZATION.getValue() + e.getMessage());
		}
	}

	private String computeInstanceData(int startIdx, byte[] data) {
		if (startIdx >= data.length){
			throw new SerializationException(ExceptionMessage.EMPTY_INSTANCE_DATA.getValue());
		}
		return new String(Arrays.copyOfRange(data, startIdx, data.length));
	}

	private Class<?> loadClass(String className) {
		try {
			Class<?> loadedClass = getClass().getClassLoader().loadClass(className);
			if (!Arrays.stream(loadedClass.getInterfaces()).collect(Collectors.toSet()).contains(Message.class)){
				throw new SerializationException(ExceptionMessage.BAD_CLASS.getValue() + className);
			}
			return loadedClass;
		} catch (ClassNotFoundException e) {
			throw new SerializationException(ExceptionMessage.BAD_CLASS_NAME.getValue() + className);
		}
	}

	private String computeClassName(int len, byte[] data) {
		if (data.length < 2 + len){
			throw new SerializationException(ExceptionMessage.NOT_CONTAIN_CLASS_NAME.getValue());
		}
		return new String(Arrays.copyOfRange(data, 2, 2 + len), StandardCharsets.UTF_8);
	}

	private int computeClassNameLen(byte[] data) {
		if (data.length < 2){
			throw new SerializationException(ExceptionMessage.NOT_CONTAIN_NAME_LEN.getValue());
		}
		return (data[0] - Byte.MIN_VALUE) * 256 + (data[1] - Byte.MIN_VALUE);
	}

	@RequiredArgsConstructor
	@Getter
	public enum ExceptionMessage {
		IS_NULL("Data is null"),
		NOT_CONTAIN_NAME_LEN("Data does not contain class name length"),
		NOT_CONTAIN_CLASS_NAME("Data does not contain class name"),
		BAD_CLASS_NAME("Bad class name: "),
		BAD_CLASS("Bad class: "),
		EMPTY_INSTANCE_DATA("Data does not contain instance data"),
		FAIL_DESERIALIZATION("Fail deserialization: ");

		private final String value;
	}
}
