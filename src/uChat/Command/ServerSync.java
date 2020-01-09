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
import uChat.Command.ACK.ServerSyncACK;
import uChat.Command.ACK.Error.InternalServerErrorACK;
import uChat.Json.MessageJson;

public class ServerSync implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_SYNC; }
	
	private int server_id;
	private int channel_id;
	
	public int getServerID() { return server_id; }
	public int getChannelID() { return channel_id; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannelID(int channel_id) { this.channel_id = channel_id; }
	
	public static ServerSync initialize(JsonElement data) {
		return new Gson().fromJson(data, ServerSync.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		Connection connection = null;
		
		try {
			// Create DB connection
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			Class.forName("org.mariadb.jdbc.Driver");
			connection = ds.getConnection();
			
			// DB: get messages
			PreparedStatement statement = connection.prepareStatement("SELECT id, server_id, channel_id, sender_id, message FROM Messages WHERE server_id=? AND channel_id=?");
			statement.setInt(1, getServerID());
			statement.setInt(2, getChannelID());
			ResultSet res = statement.executeQuery();
			ArrayList<MessageJson> messages = new ArrayList<MessageJson>();
			
			while (res.next()) {
				MessageJson message = new MessageJson();
				message.setID(res.getInt("id"));
				message.setServerID(res.getInt("server_id"));
				message.setChannelID(res.getInt("channel_id"));
				message.setSenderID(res.getInt("sender_id"));
				message.setMessage(res.getString("message"));
				messages.add(message);
			}
			
			res.close();
			statement.close();
			connection.close();
			return new ServerSyncACK(getServerID(), getChannelID(), messages);
				
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
