package uChat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Users {
	private static List<User> users;
	
	public static void addUser(User user) { users.add(user); }
	public static boolean removeUser(User user) { return users.remove(user); }
	
	public static User findUser(UUID session) {
		for (User user : users) {
			for (UUID sess : user.getSessions()) {
				if (sess.equals(session)) return user;
			}
		}
		return null;
	}
	
	static {
		if (users == null) users = new ArrayList<User>();
	}
	
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
		user.addSession(sessionID);
		PreparedStatement statement2 = connection.prepareStatement("INSERT INTO Sessions (sess_id, user_id) VALUES (?, ?)");
		statement2.setString(1, sessionID.toString());
		statement2.setInt(2, user.getID());
		statement2.execute();
		statement2.close();
		connection.close();
		
		addUser(user);
		return user;
	}
	
	public static User authenticate(UUID sessionID) throws Exception {
		User usr = findUser(sessionID);
		if (usr != null) return usr;
		
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
		user.addSession(sessionID);
		
		addUser(user);
		return user;
	}
}