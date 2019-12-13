package uChat.Json;

import java.io.Serializable;

public class UserJson implements Serializable {
private static final long serialVersionUID = 1L;
	
	private int user_id;
	private String username;
	
	public int getUserID() { return user_id; }
	public String getUserName() { return username; }
	
	public void setUserID(int user_id) { this.user_id = user_id; }
	public void setUserName(String username) { this.username = username; }
	
	public UserJson() {};
	public UserJson(int user_id, String username) {
		this.user_id = user_id;
		this.username = username;
	}
}
