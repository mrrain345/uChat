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

import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.ServerListACK;
import uChat.Command.ACK.Error.InternalServerErrorACK;
import uChat.Json.ServerJson;

public class ServerList implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_LIST; }
	
	public static ServerList initialize(JsonElement data) {
		return new ServerList();
	}
	
	public ICommandACK execute(User user, UUID session) {
		Connection connection = null;
		
		try {
			// Create DB connection
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			Class.forName("org.mariadb.jdbc.Driver");
			connection = ds.getConnection();
			
			// DB: get server list
			PreparedStatement statement = connection.prepareStatement("SELECT server_id, owner_id, name FROM Servers JOIN Server_Members ON id=server_id WHERE user_id=?");
			statement.setInt(1, user.getID());
			ResultSet res = statement.executeQuery();
			ArrayList<ServerJson> servers = new ArrayList<ServerJson>();
			
			while (res.next()) {
				ServerJson server = new ServerJson();
				server.setServerID(res.getInt("server_id"));
				server.setOwnerID(res.getInt("owner_id"));
				server.setServerName(res.getString("name"));
				servers.add(server);
			}
			
			res.close();
			statement.close();
			connection.close();
			return new ServerListACK(servers);
				
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
