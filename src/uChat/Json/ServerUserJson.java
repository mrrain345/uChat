package uChat.Json;

import java.io.Serializable;

public class ServerUserJson implements Serializable {
private static final long serialVersionUID = 1L;
	
	private int server_id;
	private int user_id;
	private String username;
	
	public int getServerID() { return server_id; }
	public int getUserID() { return user_id; }
	public String getUserName() { return username; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setUserID(int user_id) { this.user_id = user_id; }
	public void setUserName(String username) { this.username = username; }
	
	public ServerUserJson() {};
	public ServerUserJson(int server_id, int user_id, String username) {
		this.server_id = server_id;
		this.user_id = user_id;
		this.username = username;
	}
}
