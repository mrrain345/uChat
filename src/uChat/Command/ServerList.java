package uChat.Command;

import com.google.gson.JsonElement;

import uChat.CommandCode;

public class ServerList implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_LIST; }
	
	public static ServerList initialize(JsonElement data) {
		return new ServerList();
	}
	
	public String execute() {
		return null;
	}
}
