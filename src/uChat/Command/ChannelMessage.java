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
import uChat.Command.ACK.ChannelMessageACK;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.Error.InternalServerErrorACK;
import uChat.Command.ACK.Error.UnimplementedErrorACK;

public class ChannelMessage implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_MESSAGE; }
	
	private int server_id;
	private int channel_id;
	private String message;
	
	public int getServerID() { return server_id; }
	public int getChannelID() { return channel_id; }
	public String getMessage() { return message; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannelID(int channel_id) { this.channel_id = channel_id; }
	public void setMessage(String message) { this.message = message; }
	
	public static ChannelMessage initialize(JsonElement data) {
		return new Gson().fromJson(data, ChannelMessage.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		
		Connection connection = null;
		
		try {
			// Create DB connection
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			Class.forName("org.mariadb.jdbc.Driver");
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			// DB: add channel
			PreparedStatement statement = connection.prepareStatement("INSERT INTO Messages (server_id, channel_id, sender_id, message) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, getServerID());
			statement.setInt(2, getChannelID());
			statement.setInt(3, user.getID());
			statement.setString(4, getMessage());
			
			ResultSet res = statement.getGeneratedKeys();
			res.next();
			int messageID = res.getInt(1);
			res.close();
			statement.close();
			
			// Commit DB connection
			connection.commit();
			connection.close();
			return new ChannelMessageACK(getServerID(), getChannelID(), messageID, getMessage());
				
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
