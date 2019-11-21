package uChat.Command;

import uChat.CommandCode;

public class ServerLeave implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_LEAVE; }
	
	private int server_id;
	
	public int getServerID() {
		return server_id;
	}
	public void setServerID(int server_id) {
		this.server_id = server_id;
	}
}
