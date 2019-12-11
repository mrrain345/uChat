package uChat.Command.ACK;

import java.io.Serializable;

import com.google.gson.Gson;

import uChat.CommandCode;
import uChat.Command.ACK.Error.ErrorACK;

public interface ICommandACK extends Serializable {
	public CommandCode getCode();
	public default String toJSON() { return ICommandACK.toJSON(this); }
	public default String getDataJSON() { return new Gson().toJson(this); }
	
	public static String toJSON(ICommandACK ack) {
		return String.format(
			"{\"code\":%d,\"status\":0,\"data\":%s}",
			ack.getCode().getValue(),
			new Gson().toJson(ack)
		);
	}
	
	public static ICommandACK error(CommandCode command, int status, String error) {
		return new ErrorACK(command, status, error);
	}
}
