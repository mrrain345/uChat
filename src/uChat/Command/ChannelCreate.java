package uChat.Command;

import uChat.CommandCode;
import uChat.Json.Role;

public class ChannelCreate implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_CREATE; }
	
	private int server_id;
	private String channel_name;
	private Role[] roles;
	
	public int getServerID() {
		return server_id;
	}
	public void setServerID(int server_id) {
		this.server_id = server_id;
	}
	public String getChannelName() {
		return channel_name;
	}
	public void setChannelName(String channel_name) {
		this.channel_name = channel_name;
	}
	public Role[] getRoles() {
		return roles;
	}
	public void setRoles(Role[] roles) {
		this.roles = roles;
	}
}
