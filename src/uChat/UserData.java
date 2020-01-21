package uChat;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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
	
	public UserData(User user) {
		this.id = user.getID();
		this.username = user.getUsername();
	}
	
	public User getUser() {
		return Users.findUser(id);
	}
	
	public static UserData find(String username) {
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			
			Class.forName("org.mariadb.jdbc.Driver");
			Connection connection = ds.getConnection();
			
			// DB: get user
			PreparedStatement statement = connection.prepareStatement("SELECT id, username FROM Users WHERE username=? LIMIT 1");
			statement.setString(1, username);
			ResultSet res = statement.executeQuery();
			
			if (!res.next()) {
				res.close();
				statement.close();
				connection.close();
				return null;
			}
			
			UserData user = new UserData(res.getInt("id"), res.getString("username"));
			res.close();
			statement.close();
			return user;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
