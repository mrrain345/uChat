package uChat.Command;

import uChat.CommandCode;

public class UserGetRoles implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.USER_GET_ROLES; }
	
	private int server_id;
	private int user_id;
	
	public int getServerID() {
		return server_id;
	}
	public void setServerID(int server_id) {
		this.server_id = server_id;
	}
	public int getUserID() {
		return user_id;
	}
	public void setUserID(int user_id) {
		this.user_id = user_id;
	}
}
