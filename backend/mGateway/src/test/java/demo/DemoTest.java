package demo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.gateway.in.InFactoryImpl;
import ru.cwe.conversation.gateway.in.InGateway;
import ru.cwe.conversation.gateway.out.OutFactoryImpl;
import ru.cwe.conversation.gateway.out.OutGateway;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import utils.TestMessageContainer;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

public class DemoTest {

	private static final String HOST = "localhost";
	private static final int PORT = 5000;

	@SneakyThrows
	@Test
	void start() {

		TestMessageContainer<PayloadMessage> reqContainer = new TestMessageContainer<>();
		TestMessageContainer<PayloadMessage> resContainer = new TestMessageContainer<>();
		final InGateway inGateway = new InFactoryImpl().create(
			reqContainer,
			resContainer,
			PORT
		);

		TestMessageContainer<ConfirmationMessage> confirmContainer = new TestMessageContainer<>();
		final OutGateway outGateway = new OutFactoryImpl().create(
			confirmContainer,
			HOST,
			PORT
		);

		Runnable serverRunnable = () -> {
			System.out.println(" ----- serverRunnable -----");
			inGateway.start();
		};
		new Thread(serverRunnable).start();
		Thread.sleep(500);

		Runnable clientRunnable = () -> {
			System.out.println(" ----- clientRunnable ----- ");

			TestPayloadMessage message = new TestPayloadMessage(
				1,
				2,
				MessageType.REQUEST,
				Fakers.base().uuid().uuid(),
				Fakers.base().string().string(),
				Fakers.base().string().string(),
				Fakers.address().address(),
				Fakers.address().address()
			);
			outGateway.send(message);

			message = new TestPayloadMessage(
				1,
				2,
				MessageType.RESPONSE,
				Fakers.base().uuid().uuid(),
				Fakers.base().string().string(),
				Fakers.base().string().string(),
				Fakers.address().address(),
				Fakers.address().address()
			);
			outGateway.send(message);

			message = new TestPayloadMessage(
				1,
				2,
				MessageType.REQUEST,
				Fakers.base().uuid().uuid(),
				Fakers.base().string().string(),
				Fakers.base().string().string(),
				Fakers.address().address(),
				Fakers.address().address()
			);
			outGateway.send(message);
		};
		new Thread(clientRunnable).start();

		Thread.sleep(1_000);

		System.out.println("req size: " + reqContainer.getMessages().size());
		System.out.println("resp size: " + resContainer.getMessages().size());
		System.out.println("confirm size: " + confirmContainer.getMessages().size());

		inGateway.shutdown();
		outGateway.shutdown();
	}
}
