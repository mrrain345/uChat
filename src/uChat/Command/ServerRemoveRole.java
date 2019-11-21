package uChat.Command;

import uChat.CommandCode;

public class ServerRemoveRole implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_REMOVE_ROLE; }
	
	private int server_id;
	private int role_id;
	
	public int getServerID() {
		return server_id;
	}
	public void setServerID(int server_id) {
		this.server_id = server_id;
	}
	public int getRoleID() {
		return role_id;
	}
	public void setRoleID(int role_id) {
		this.role_id = role_id;
	}
}
