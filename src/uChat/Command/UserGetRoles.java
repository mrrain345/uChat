package uChat.Command;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;

public class UserGetRoles implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.USER_GET_ROLES; }
	
	private int server_id;
	private int user_id;
	
	public int getServerID() { return server_id; }
	public int getUserID() { return user_id; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setUserID(int user_id) { this.user_id = user_id; }
	
	public static UserGetRoles initialize(JsonElement data) {
		return new Gson().fromJson(data, UserGetRoles.class);
	}
	
	public String execute() {
		return null;
	}
}
