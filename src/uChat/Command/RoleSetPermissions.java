package uChat.Command;

import uChat.CommandCode;
import uChat.Json.RolePermission;

public class RoleSetPermissions implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.ROLE_SET_PERMISSIONS; }
	
	private int server_id;
	private int role_id;
	private RolePermission[] permissions;
	
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

	public RolePermission[] getPermissions() {
		return permissions;
	}

	public void setPermissions(RolePermission[] permissions) {
		this.permissions = permissions;
	}
}
