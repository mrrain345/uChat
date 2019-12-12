package uChat.Command.ACK;

import uChat.CommandCode;

public class ServerAddUserACK implements ICommandACK{
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_ADD_USER_ACK; }
	
	private int server_id;
	private int user_id;

	public int getServerID() { return server_id; }
	public int getUserID() { return user_id; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setUserID(int user_id) { this.user_id = user_id; }
	
	public ServerAddUserACK() {}
	public ServerAddUserACK(int server_id, int user_id) {
		this.server_id = server_id;
		this.user_id = user_id;
	}
}
