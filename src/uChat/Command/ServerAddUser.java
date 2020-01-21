package uChat.Command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.Server;
import uChat.User;
import uChat.UserData;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.ServerAddUserACK;
import uChat.Command.ACK.Error.InternalServerErrorACK;
import uChat.Json.ServerUserJson;

public class ServerAddUser implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_ADD_USER; }
	
	private int server_id;
	private String username;
	
	public int getServerID() { return server_id; }
	public String getUsername() { return username; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setUsername(String username) { this.username = username; }
	
	public static ServerAddUser initialize(JsonElement data) {
		return new Gson().fromJson(data, ServerAddUser.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		
		Connection connection = null;
		
		UserData userData = UserData.find(getUsername());
		if (userData == null) return new ServerAddUserACK(getServerID(), false, getUsername());
		
		try {
			// Create DB connection
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			Class.forName("org.mariadb.jdbc.Driver");
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			
			
			// DB: add channel
			PreparedStatement statement = connection.prepareStatement("INSERT INTO Server_Members (server_id, user_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, getServerID());
			statement.setInt(2, userData.getID());
			statement.execute();

			statement.close();
			
			// Commit DB connection
			connection.commit();
			connection.close();
			
			// Event: USER_ADDED
			Server server = new Server(getServerID());
			server.sendUserAddedEvent(new ServerUserJson(getServerID() ,userData.getID(), userData.getUsername()));
			
			return new ServerAddUserACK(getServerID(), userData.getID(), userData.getUsername());
				
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
					connection.close();
				}
				catch (SQLException e1) { e1.printStackTrace(); }
			}
			e.printStackTrace();
			return new InternalServerErrorACK(this);
		}
	}
}
