package uChat;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Server implements Serializable {
	private static final long serialVersionUID = 1L;

	private static List<Server> servers;
	
	private int id;
	private String name;
	private int ownerID;
	private Date createdAt;
	private List<Channel> channels;
	private List<UserData> users;
	
	
	public int getID() { return id; }
	public String getName() { return name; }
	public int getOwnerID() { return ownerID; }
	public Date getCreatedAt() { return createdAt; }
	public List<Channel> getChannels() { return channels; }
	public List<UserData> getUsers() { return users; }

	static {
		servers = new ArrayList<Server>();
	}

	public Server() {
		this.channels = new ArrayList<Channel>();
		this.users = new ArrayList<UserData>();
	}
	
	public Server(int id) {
		try {
			initialize(id);
			servers.add(this);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize(int id) throws IllegalArgumentException {
		this.id = id;
		this.channels = new ArrayList<Channel>();
		this.users = new ArrayList<UserData>();
		
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			
			Class.forName("org.mariadb.jdbc.Driver");
			Connection connection = ds.getConnection();
			
			// DB: get server
			PreparedStatement statement = connection.prepareStatement("SELECT id, name, owner_id FROM Servers WHERE id=? LIMIT 1");
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			
			if (!res.next()) {
				res.close();
				statement.close();
				connection.close();
				throw new IllegalArgumentException("Server '" + id + "' not found");
			}
			
			this.name = res.getString("name");
			this.ownerID = res.getInt("owner_id");
			this.createdAt = res.getDate("created_at");
			res.close();
			statement.close();
			
			// DB: get server channels
			statement = connection.prepareStatement("SELECT id, server_id, name FROM Channels WHERE server_id=?");
			statement.setInt(1, id);
			res = statement.executeQuery();
			
			while (res.next()) {
				Channel channel = new Channel(id, res.getInt("id"), res.getString("name"));
				channels.add(channel);
			}
			
			res.close();
			statement.close();
			
			// DB: get server members
			statement = connection.prepareStatement("SELECT server_id, user_id, username FROM Server_Members JOIN Users ON user_id=Users.id WHERE server_id=?");
			statement.setInt(1, id);
			res = statement.executeQuery();
			
			while (res.next()) {
				UserData user = new UserData(res.getInt("user_id"), res.getString("server_id"));
				users.add(user);
			}
			
			res.close();
			statement.close();
			
			connection.close();
			
		} catch (SQLException | NamingException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
