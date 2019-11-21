package uChat.Command;

import uChat.CommandCode;

public class FriendReceive implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.FRIEND_RECEIVE; }

	private int sender_id;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSenderID() {
		return sender_id;
	}

	public void setSenderID(int sender_id) {
		this.sender_id = sender_id;
	}
}
