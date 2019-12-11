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
import uChat.Command.ACK.ChannelListACK;
import uChat.Command.ACK.ICommandACK;
import uChat.Json.ChannelJson;

public class ChannelList implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_LIST; }
	
	private int server_id;
	
	public int getServerID() { return server_id; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	
	public static ChannelList initialize(JsonElement data) {
		return new Gson().fromJson(data, ChannelList.class);
	}
	
	public String execute(User user, UUID session) {
		Connection connection = null;
		
		try {
			// Create DB connection
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			Class.forName("org.mariadb.jdbc.Driver");
			connection = ds.getConnection();
			
			// DB: get server list
			PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM Channels WHERE server_id=?");
			statement.setInt(1, getServerID());
			ResultSet res = statement.executeQuery();
			ArrayList<ChannelJson> channels = new ArrayList<ChannelJson>();
			
			while (res.next()) {
				ChannelJson channel = new ChannelJson();
				channel.setChannelID(res.getInt("id"));
				channel.setChannelName(res.getString("name"));
				channels.add(channel);
			}
			
			res.close();
			statement.close();
			connection.close();
			return new ChannelListACK(getServerID(), channels).toJSON();
				
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.close();
				}
				catch (SQLException e1) { e1.printStackTrace(); }
			}
			e.printStackTrace();
			return ICommandACK.error(CommandCode.CHANNEL_LIST_ACK, 1, "Internal server error");
		}
	}
}
