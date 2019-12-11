package uChat.Command;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.Error.UnimplementedErrorACK;

public class RoleGetPermissions implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.ROLE_GET_PERMISSIONS; }
	
	private int server_id;
	private int role_id;
	
	public int getServerID() { return server_id; }
	public int getRoleID() { return role_id; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setRoleID(int role_id) { this.role_id = role_id; }
	
	public static RoleGetPermissions initialize(JsonElement data) {
		return new Gson().fromJson(data, RoleGetPermissions.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		return new UnimplementedErrorACK(this);
	}
}
