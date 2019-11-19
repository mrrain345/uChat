package uChat;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class User implements Serializable {
	private int id;
	private String username;
	private boolean isBot;
	private UUID sessionID;
	
	public User() {}
	
	public int getID() { return id; }
	public String getUsername() { return username; }
	public boolean isBot() { return isBot; }
	public UUID getSessionID() { return sessionID; }
	
	public void setID(int id) { this.id = id; }
	public void setUsername(String username) { this.username = username; }
	public void setBot(boolean isBot) { this.isBot = isBot; }
	public void setSessionID(UUID sessionID) { this.sessionID = sessionID; }
	
	public static User authenticate(LoginJson login) throws Exception {
		return authenticate(login.getUsername(), login.getPassword());
	}
	
	public static User authenticate(String username, String password) throws Exception {
		Context context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
		
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = ds.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("SELECT id, username, is_bot FROM Users WHERE username=? AND password=? LIMIT 1");
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet res = statement.executeQuery();
		
		if (!res.next()) {
			res.close();
			statement.close();
			connection.close();
			return null;
		}
		
		User user = new User();
		user.setID(res.getInt("id"));
		user.setUsername(res.getString("username"));
		user.setBot(res.getBoolean("is_bot"));
		res.close();
		statement.close();
		
		UUID sessionID = UUID.randomUUID();
		user.setSessionID(sessionID);
		PreparedStatement statement2 = connection.prepareStatement("INSERT INTO Sessions (sess_id, user_id) VALUES (?, ?)");
		statement2.setString(1, sessionID.toString());
		statement2.setInt(2, user.getID());
		statement2.execute();
		statement2.close();
		connection.close();
		
		return user;
	}
	
	public static User authenticate(UUID sessionID) throws Exception {
		Context context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
		
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = ds.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("SELECT id, username, is_bot FROM Sessions JOIN Users ON user_id=id WHERE sess_id=? LIMIT 1");
		statement.setString(1, sessionID.toString());
		ResultSet res = statement.executeQuery();
		
		if (!res.next()) return null;
		User user = new User();
		user.setID(res.getInt("id"));
		user.setUsername(res.getString("username"));
		user.setBot(res.getBoolean("is_bot"));
		user.setSessionID(sessionID);
		return user;
	}
	
	@Override
	public String toString() {
		return getUsername();
	}
	
	@Override
	public int hashCode() {
		return getSessionID().hashCode();
	}
}
