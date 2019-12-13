package uChat.Command.ACK;

import java.util.List;

import uChat.CommandCode;
import uChat.Json.UserJson;

public class ServerGetUsersACK implements ICommandACK{
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_GET_USERS_ACK; }

	private int server_id;
	private List<UserJson> users;
	
	public int getServerID() { return server_id; }
	public List<UserJson> getChannels() { return users; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setUsers(List<UserJson> users) { this.users = users; }
	
	public ServerGetUsersACK() {}
	public ServerGetUsersACK(int server_id, List<UserJson> users) {
		this.server_id = server_id;
		this.users = users;
	}
}
