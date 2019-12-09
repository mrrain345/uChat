package uChat.Command;

import java.util.UUID;

import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;

public class Heartbeat implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.HEARTBEAT; }
	
	public static Heartbeat initialize(JsonElement data) {
		return new Heartbeat();
	}
	
	public String execute(User user, UUID session) {
		return null;
	}
}
