package utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

// TODO: 27.03.2023 rename & move TestBuffers to mCommon
public final class TestBuffers {
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	public static ByteBuf create(){
		return new UnpooledByteBufAllocator(true).buffer();
	}

	public static void writeInt(ByteBuf buffer, int value){
		buffer.writeInt(value);
	}

	public static void writeUuid(ByteBuf buffer, UUID value){
		buffer.writeLong(value.getLeastSignificantBits());
		buffer.writeLong(value.getMostSignificantBits());
	}

	public static void writeMessageType(ByteBuf buffer, MessageType value){
		buffer.writeInt(value.getValue());
	}

	public static void writeConfirmationResult(ByteBuf buffer, ConfirmationResult value){
		buffer.writeInt(value.getValue());
	}

	public static void writeString(ByteBuf buffer, String value){
		int len = value != null ? value.length() : -1;
		buffer.writeInt(len);
		if (len != -1){
			buffer.writeCharSequence(value, CHARSET);
		}
	}

	public static void writeAddress(ByteBuf buffer, Address value){
		writeString(buffer, value.getHost());
		writeInt(buffer, value.getPort());
	}

	public static int readInt(ByteBuf buffer){
		return buffer.readInt();
	}

	public static UUID readUuid(ByteBuf buffer){
		long low = buffer.readLong();
		long high = buffer.readLong();
		return new UUID(high, low);
	}

	public static MessageType readMessageType(ByteBuf buffer){
		return MessageType.valueOf(readInt(buffer));
	}

	public static ConfirmationResult readConfirmationResult(ByteBuf buffer){
		return ConfirmationResult.valueOf(buffer.readInt());
	}

	public static String readString(ByteBuf buffer){
		int len = readInt(buffer);
		String result = null;
		if (len == 0){
			result = "";
		} else if (len > 0){
			result = buffer.readCharSequence(len, CHARSET).toString();
		}
		return result;
	}

	public static Address readAddress(ByteBuf buffer){
		String host = readString(buffer);
		int port = readInt(buffer);
		return new TestAddress(host, port);
	}
}
