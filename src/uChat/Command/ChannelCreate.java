package uChat.Command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import uChat.Command.ACK.ServerCreateACK;
import uChat.Command.ACK.ChannelCreateACK;
import uChat.Command.ACK.Error.InternalServerErrorACK;
import uChat.Command.ACK.Error.UnimplementedErrorACK;
import uChat.Json.ChannelJson;
import uChat.Json.Role;

public class ChannelCreate implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_CREATE; }
	
	private int server_id;
	private String channel_name;
	
	public int getServerID() { return server_id; }
	public String getChannelName() { return channel_name; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannelName(String channel_name) { this.channel_name = channel_name; }
	
	public static ChannelCreate initialize(JsonElement data) {
		return new Gson().fromJson(data, ChannelCreate.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		if (getChannelName().length() < 3 || getChannelName().length() > 35) {
			return ICommandACK.error(CommandCode.CHANNEL_CREATE_ACK, 2, "Incorrect channel name (min: 3, max: 35 characters)");
		}
		
		Connection connection = null;
		
		try {
			// Create DB connection
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			Class.forName("org.mariadb.jdbc.Driver");
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			// DB: add channel
			PreparedStatement statement = connection.prepareStatement("INSERT INTO Channels (server_id, name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, getServerID());
			statement.setString(2, getChannelName());
			statement.execute();
			
			ResultSet res = statement.getGeneratedKeys();
			res.next();
			int channelID = res.getInt(1);
			int serverID = getServerID();
			res.close();
			statement.close();
			
			// Commit DB connection
			connection.commit();
			connection.close();
			return new ChannelCreateACK(serverID, channelID, getChannelName());
				
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
