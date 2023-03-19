package ru.cwe.conversation.address;

public class AddressBuilder {
	private static final int MIN_PORT_VALUE = 0;
	private static final int MAX_PORT_VALUE = 65535;

	private final StringBuilder exceptionMessage = new StringBuilder();

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
		checkHost();
		checkPort();
		if (!exceptionMessage.isEmpty()){
			throw new AddressBuildingRuntimeException(exceptionMessage.toString());
		}

		return null;
	}

	private void checkHost() {
		if (host == null){
			exceptionMessage.append("host is not set");
		}
	}

	private void checkPort() {
		if (port == null){
			if (!exceptionMessage.isEmpty()){
				exceptionMessage.append("; ");
			}
			exceptionMessage.append("port is not set");
		} else if (port < MIN_PORT_VALUE || port > MAX_PORT_VALUE) {
			if (!exceptionMessage.isEmpty()){
				exceptionMessage.append("; ");
			}
			exceptionMessage.append("port is out of range (").append(port).append(")");
		}
	}
}
