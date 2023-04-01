package utils.faker;

import com.github.javafaker.Faker;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import utils.TestAddress;

import java.util.UUID;

public final class Fakers {
	private static final Faker core = new Faker();

	public static Faker core(){
		return core;
	}

	public static BaseFaker base(){
		return new BaseFaker(core());
	}

	// TODO: 01.04.2023 rename
	public static AddressFaker address(){
		return new AddressFaker(base());
	}

	// TODO: 01.04.2023 rename
	public static MessageFaker message(){
		return new MessageFaker(base());
	}



	// TODO: 01.04.2023 del
	public static String host(){
		return "host-" + String.valueOf(core().number().randomNumber());
	}

	// TODO: 01.04.2023 del
	public static Integer port(){
		return core.number().numberBetween(0, 65536);
	}

	// TODO: 01.04.2023 del
	public static Address addressOld(){
		return new TestAddress(host(), port());
	}

	// TODO: 01.04.2023 del
	public static int version(){
		return core().number().numberBetween(Versions.MIN, Versions.MAX + 1);
	}

	// TODO: 01.04.2023 del
	public static int priority(){
		return core().number().numberBetween(Priorities.MIN, Priorities.MAX + 1);
	}

	// TODO: 01.04.2023 del
	public static UUID uuid(){
		return UUID.randomUUID();
	}

	// TODO: 01.04.2023 del
	public static String string(){
		return "randomString" + String.valueOf(core().number().randomNumber());
	}

	// TODO: 01.04.2023 del
	public static ConfirmationResult confirmationResult(){
		return ConfirmationResult.valueOf(
			core().number().numberBetween(
				ConfirmationResult.INVALID.getValue(),
				ConfirmationResult.RESPONSE.getValue() + 1)
		);
	}
}
