package uChat.Command;

import uChat.CommandCode;

public class FriendList implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.FRIEND_LIST; }

}
