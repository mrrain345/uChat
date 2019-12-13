package uChat.Command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.ServerGetUsersACK;
import uChat.Command.ACK.Error.InternalServerErrorACK;
import uChat.Json.UserJson;

public class ServerGetUsers implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_GET_USERS; }
	
	private int server_id;
	
	public int getServerID() { return server_id; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	
	public static ServerGetUsers initialize(JsonElement data) {
		return new Gson().fromJson(data, ServerGetUsers.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		Connection connection = null;
		
		try {
			// Create DB connection
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			Class.forName("org.mariadb.jdbc.Driver");
			connection = ds.getConnection();
			
			// DB: get server users
			PreparedStatement statement = connection.prepareStatement("SELECT id, username FROM Users JOIN Server_Members ON id=user_id WHERE server_id=?");
			statement.setInt(1, getServerID());
			ResultSet res = statement.executeQuery();
			ArrayList<UserJson> users = new ArrayList<UserJson>();
			
			while (res.next()) {
				UserJson userJson = new UserJson();
				userJson.setUserID(res.getInt("id"));
				userJson.setUserName(res.getString("username"));
				users.add(userJson);
			}
			
			res.close();
			statement.close();
			connection.close();
			return new ServerGetUsersACK(getServerID(), users);
				
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.close();
				}
				catch (SQLException e1) { e1.printStackTrace(); }
			}
			e.printStackTrace();
			return new InternalServerErrorACK(this);
		}
	}
}
