package uChat.Command;

import uChat.CommandCode;

public class FriendMessage implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.FRIEND_MESSAGE; }

	private int reveiver_id;
	private String message;
	
	public int getReveiverID() {
		return reveiver_id;
	}
	public void setReveiverID(int reveiver_id) {
		this.reveiver_id = reveiver_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
