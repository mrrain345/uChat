package uChat.Command.ACK;

import java.io.Serializable;

import com.google.gson.Gson;

import uChat.CommandCode;

public interface ICommandACK extends Serializable {
	public CommandCode getCode();
	public default String toJSON() { return ICommandACK.toJSON(this); }
	
	public static String toJSON(ICommandACK ack) {
		return String.format(
			"{\"code\":%d,\"status\":0,\"data\":%s}",
			ack.getCode().getValue(),
			new Gson().toJson(ack)
		);
	}
	
	public static String error(CommandCode ack, int status, String error) {
		return String.format(
			"{\"code\":%d,\"status\":%d,\"error\":\"%s\"}",
			ack.getValue(),
			status,
			error
		);
	}
}
