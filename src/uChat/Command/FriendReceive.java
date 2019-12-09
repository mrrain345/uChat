package uChat.Command;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;

public class FriendReceive implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.FRIEND_RECEIVE; }

	private int sender_id;
	private String message;

	public String getMessage() { return message; }
	public int getSenderID() { return sender_id; }
	
	public void setMessage(String message) { this.message = message; }
	public void setSenderID(int sender_id) { this.sender_id = sender_id; }
	
	public static FriendReceive initialize(JsonElement data) {
		return new Gson().fromJson(data, FriendReceive.class);
	}
	
	public String execute(User user, UUID session) {
		return null;
	}
}
