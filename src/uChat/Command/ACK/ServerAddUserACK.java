package uChat.Command.ACK;

import uChat.CommandCode;

public class ServerAddUserACK implements ICommandACK{
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_ADD_USER_ACK; }
	
	private boolean success;
	private int server_id;
	private int user_id;
	private String username;

	public boolean isSuccess() { return success; }
	public int getServerID() { return server_id; }
	public int getUserID() { return user_id; }
	public String getUsername() { return username; }
	
	public void setSuccess(boolean success) { this.success = success; }
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setUserID(int user_id) { this.user_id = user_id; }
	public void setUsername(String username) { this.username = username; }
	
	public ServerAddUserACK() {}
	
	public ServerAddUserACK(int server_id, boolean success, String username) {
		if (success) throw new IllegalArgumentException("Argument must be 'false'");
		this.success = false;
		this.server_id = server_id;
		this.user_id = 0;
		this.username = username;
	}
	
	public ServerAddUserACK(int server_id, int user_id, String username) {
		this.success = true;
		this.server_id = server_id;
		this.user_id = user_id;
		this.username = username;
	}
}
