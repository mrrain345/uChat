package uChat.Command.ACK;

import java.io.Serializable;

import uChat.CommandCode;

public class ServerCreateACK implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
	
	@Override
	public String toString() {
		return String.format(
			"{ \"code\": %d, \"status\": 0, \"server_id\": %d, \"server_name\": \"%s\" }",
			CommandCode.SERVER_CREATE_ACK.getValue(),
			server_id,
			server_name
		);
	}
}
