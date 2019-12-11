package uChat.Json;

import java.io.Serializable;

public class ServerJson implements Serializable {
	private static final long serialVersionUID = 1L;

	private int server_id;
	private int owner_id;
	private String server_name;
	
	public int getServerID() { return server_id; }
	public int getOwnerID() { return owner_id; }
	public String getServerName() { return server_name; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setOwnerID(int owner_id) { this.owner_id = owner_id; }
	public void setServerName(String server_name) { this.server_name = server_name; }
	
	public ServerJson() {}
	public ServerJson(int server_id, int owner_id, String server_name) {
		this.server_id = server_id;
		this.owner_id = owner_id;
		this.server_name = server_name;
	}
}
