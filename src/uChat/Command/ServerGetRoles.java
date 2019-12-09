package uChat.Command;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;

public class ServerGetRoles implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_GET_ROLES; }
	
	private int server_id;
	
	public int getServerID() { return server_id; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	
	public static ServerGetRoles initialize(JsonElement data) {
		return new Gson().fromJson(data, ServerGetRoles.class);
	}
	
	public String execute() {
		return null;
	}
}
