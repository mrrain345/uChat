package uChat.Command;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.Error.UnimplementedErrorACK;

public class FriendMessage implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.FRIEND_MESSAGE; }

	private int reveiver_id;
	private String message;
	
	public int getReveiverID() { return reveiver_id; }
	public String getMessage() { return message; }
	
	public void setReveiverID(int reveiver_id) { this.reveiver_id = reveiver_id; }
	public void setMessage(String message) { this.message = message; }
	
	public static FriendMessage initialize(JsonElement data) {
		return new Gson().fromJson(data, FriendMessage.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		return new UnimplementedErrorACK(this);
	}
}
