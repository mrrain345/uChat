package uChat.Command;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;

public class FriendRemove implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.FRIEND_REMOVE; }
	
	private int user_id;
	
	public int getUserID() { return user_id; }
	
	public void setUserID(int user_id) { this.user_id = user_id; }
	
	public static FriendRemove initialize(JsonElement data) {
		return new Gson().fromJson(data, FriendRemove.class);
	}
	
	public String execute() {
		return null;
	}
}
