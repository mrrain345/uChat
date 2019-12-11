package uChat.Command.ACK;

import uChat.CommandCode;

public class ServerCreateACK implements ICommandACK {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_CREATE_ACK; }
	
	private int server_id;
	private String server_name;
	
	public int getServerID() { return server_id; }
	public String getServerName() { return server_name; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setServerName(String server_name) { this.server_name = server_name; }
	
	public ServerCreateACK() {}
	public ServerCreateACK(int id, String name) {
		this.server_id = id;
		this.server_name = name;
	}
}
