package uChat.Command;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;

public class FriendAdd implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.FRIEND_ADD; }

	private String username;
	
	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }
	
	public static FriendAdd initialize(JsonElement data) {
		return new Gson().fromJson(data, FriendAdd.class);
	}
	
	public String execute(User user, UUID session) {
		return null;
	}
}
