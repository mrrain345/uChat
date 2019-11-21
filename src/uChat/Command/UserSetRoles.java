package uChat.Command;

import uChat.CommandCode;
import uChat.Json.Role;

public class UserSetRoles implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.USER_SET_ROLES; }
	
	private int server_id;
	private int user_id;
	private Role[] roles;
	
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
	public Role[] getRoles() {
		return roles;
	}
	public void setRoles(Role[] roles) {
		this.roles = roles;
	}
}
