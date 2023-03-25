package ru.cwe.conversation.address;

import ru.cwe.conversation.exception.AbsentFieldRuntimeExceptionBuilderImpl;
import ru.cwe.utils.port.Ports;

import java.util.Optional;
import java.util.function.Function;

public final class AddressBuilder {
	private final ExceptionBuilder exceptionBuilder = new ExceptionBuilder(AddressBuildingRuntimeException::new);

	private String host;
	private Integer port;

	public AddressBuilder host(String host){
		this.host = host;
		return this;
	}

	public AddressBuilder port(int port){
		this.port = port;
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
		public ExceptionBuilder(Function<String, RuntimeException> creator) {
			super(creator);
		}

		public ExceptionBuilder checkPort(Integer port){
			if (port != null && Ports.checkInRange(port) != 0){
				if (!messageSB.isEmpty()){
					messageSB.append("; ");
				}
				messageSB.append("port is out of range (").append(port).append(")");
			}
			return this;
		}
	}
}
