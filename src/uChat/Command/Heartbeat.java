package uChat.Command;

import java.util.UUID;

import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.Error.UnimplementedErrorACK;

public class Heartbeat implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.HEARTBEAT; }
	
	public static Heartbeat initialize(JsonElement data) {
		return new Heartbeat();
	}
	
	public ICommandACK execute(User user, UUID session) {
		return new UnimplementedErrorACK(this);
	}
}
