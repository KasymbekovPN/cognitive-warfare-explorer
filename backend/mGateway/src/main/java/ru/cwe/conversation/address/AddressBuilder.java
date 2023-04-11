package ru.cwe.conversation.address;

import ru.cwe.conversation.exception.AbsentFieldRuntimeExceptionBuilderImpl;
import ru.cwe.utils.port.Ports;

import java.util.Optional;
import java.util.function.Function;

public final class AddressBuilder {
	private final ExceptionBuilder exceptionBuilder = new ExceptionBuilder(AddressBuildingRuntimeException::new);

	private String host;
	private Integer port;

	public static AddressBuilder builder(){
		return new AddressBuilder();
	}

	private AddressBuilder() {
	}

	public AddressBuilder host(final String host){
		this.host = host;
		return this;
	}

	public AddressBuilder port(final int port){
		this.port = port;
		return this;
	}

	public AddressBuilder reset(){
		this.host = null;
		this.port = null;
		return this;
	}

	public Address build(){
		exceptionBuilder
			.checkField("host", host)
			.checkField("port", port);
		Optional<RuntimeException> maybeException = exceptionBuilder
			.checkPort(port)
			.build();

		if (maybeException.isPresent()){
			throw maybeException.get();
		}

		return new AddressImpl(host, port);
	}

	private static class ExceptionBuilder extends AbsentFieldRuntimeExceptionBuilderImpl{
		public ExceptionBuilder(final Function<String, RuntimeException> creator) {
			super(creator);
		}

		public ExceptionBuilder checkPort(final Integer port){
			if (port != null && Ports.checkInRange(port) != 0){
				appendPartDelimiterAndGet()
					.append("port is out of range (")
					.append(port)
					.append(")");
			}
			return this;
		}
	}
}
