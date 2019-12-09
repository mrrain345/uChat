package uChat.Command;

import com.google.gson.JsonElement;

import uChat.CommandCode;

public class Heartbeat implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.HEARTBEAT; }
	
	public static Heartbeat initialize(JsonElement data) {
		return new Heartbeat();
	}
	
	public String execute() {
		return null;
	}
}
