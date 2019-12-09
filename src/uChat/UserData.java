package uChat;

import java.io.Serializable;

public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String username;
	
	public int getID() { return id; }
	public String getUsername() { return username; }
	
	public void setID(int id) { this.id = id; }
	public void setUsername(String username) { this.username = username; }
	
	public UserData() {}
	
	public UserData(int id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public User getUser() {
		return Users.findUser(id);
	}
}
