package uChat.Command;

import java.util.UUID;

import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;

public class ServerList implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_LIST; }
	
	public static ServerList initialize(JsonElement data) {
		return new ServerList();
	}
	
	public String execute(User user, UUID session) {
		return null;
	}
}
