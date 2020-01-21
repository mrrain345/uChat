package uChat.Json;

import java.io.Serializable;

public class RegisterJson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String confirm_password;
	private String email;
	
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	public String getConfirmPassword() { return confirm_password; }
	public String getEmail() { return email; }
	
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
	public void setConfirmPassword(String confirm_password) { this.confirm_password = confirm_password; }
	public void setEmail(String email) { this.email = email; }
}
