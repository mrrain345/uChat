package uChat.Command;

import java.util.UUID;

import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;

public class FriendList implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.FRIEND_LIST; }

	public static FriendList initialize(JsonElement data) {
		return new FriendList();
	}
	
	public String execute(User user, UUID session) {
		return null;
	}
}
