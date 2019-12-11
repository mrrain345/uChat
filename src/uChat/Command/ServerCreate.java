package uChat.Command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.ServerCreateACK;
import uChat.Command.ACK.Error.InternalServerErrorACK;
import uChat.Command.ACK.Error.UnimplementedErrorACK;

public class ServerCreate implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_CREATE; }
	
	private String server_name;
	
	public String getServerName() { return server_name; }

	public void setServerName(String server_name) { this.server_name = server_name; }
	
	public static ServerCreate initialize(JsonElement data) {
		return new Gson().fromJson(data, ServerCreate.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		if (getServerName().length() < 3 || getServerName().length() > 80) {
			return ICommandACK.error(CommandCode.SERVER_CREATE_ACK, 2, "Incorrect server name (min: 3, max: 80 characters)");
		}
		
		Connection connection = null;
		
		try {
			// Create DB connection
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			Class.forName("org.mariadb.jdbc.Driver");
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			// DB: create server
			PreparedStatement statement = connection.prepareStatement("INSERT INTO Servers (name, owner_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, getServerName());
			statement.setInt(2, user.getID());
			statement.execute();
			
			ResultSet res = statement.getGeneratedKeys();
			res.next();
			int serverID = res.getInt(1);
			res.close();
			statement.close();
			
			// DB: add user to new server
			statement = connection.prepareStatement("INSERT INTO Server_Members (server_id, user_id) VALUES (?, ?)");
			statement.setInt(1, serverID);
			statement.setInt(2, user.getID());
			statement.execute();
			statement.close();
			
			// DB: create 'general' channel
			statement = connection.prepareStatement("INSERT INTO Channels (server_id, name) VALUES (?, \"general\")");
			statement.setInt(1, serverID);
			statement.execute();
			statement.close();
			
			// Commit DB connection
			connection.commit();
			connection.close();
			return new ServerCreateACK(serverID, getServerName());
				
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
