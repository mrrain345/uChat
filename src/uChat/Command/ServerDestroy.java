package uChat.Command;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;

public class ServerDestroy implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_DESTROY; }
	
	private int server_id;
	
	public int getServerID() { return server_id; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	
	public static ServerDestroy initialize(JsonElement data) {
		return new Gson().fromJson(data, ServerDestroy.class);
	}
	
	public String execute(User user, UUID session) {
		return null;
	}
}
