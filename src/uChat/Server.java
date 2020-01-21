package uChat;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import uChat.Json.MessageJson;
import uChat.Json.ServerChannelJson;
import uChat.Json.ServerUserJson;

public class Server implements Serializable {
	private static final long serialVersionUID = 1L;

	private static List<Server> servers;
	
	private int id;
	private String name;
	private int ownerID;
	private Date createdAt;
	private Hashtable<Integer, Channel> channels;
	private Hashtable<Integer, UserData> users;
	
	
	public int getID() { return id; }
	public String getName() { return name; }
	public int getOwnerID() { return ownerID; }
	public Date getCreatedAt() { return createdAt; }
	public Hashtable<Integer, Channel> getChannels() { return channels; }
	public Hashtable<Integer, UserData> getUsers() { return users; }

	public void addUser(UserData user) { users.put(user.getID(), user); }
	public void addUser(User user) { users.put(user.getID(), new UserData(user)); }
	
	public UserData getUser(int id) { return users.get(id); }	
	public Channel getChannel(int id) { return channels.get(id); }
	
	static {
		servers = new ArrayList<Server>();
	}

	public Server() {
		this.channels = new Hashtable<Integer, Channel>();
		this.users = new Hashtable<Integer, UserData>();
	}
	
	public Server(int id) {
		try {
			initialize(id);
			servers.add(this);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessageEvent(MessageJson data) {
		for (UserData userData : getUsers().values()) {
			User user = userData.getUser();
			if (user != null) user.sendMessageEvent(data);
		}
	}
	
	public void sendChannelCreatedEvent(ServerChannelJson data) {
		for (UserData userData : getUsers().values()) {
			User user = userData.getUser();
			if (user != null) user.sendChannelCreatedEvent(data);
		}
	}
	
	public void sendChannelRemovedEvent(ServerChannelJson data) {
		for (UserData userData : getUsers().values()) {
			User user = userData.getUser();
			if (user != null) user.sendChannelRemovedEvent(data);
		}
	}
	
	public void sendUserAddedEvent(ServerUserJson data) {
		for (UserData userData : getUsers().values()) {
			User user = userData.getUser();
			if (user != null) user.sendUserAddedEvent(data);
		}
	}
	
	public void sendUserRemovedEvent(ServerUserJson data) {
		for (UserData userData : getUsers().values()) {
			User user = userData.getUser();
			if (user != null) user.sendUserRemovedEvent(data);
		}
	}
	
	public void initialize(int id) throws IllegalArgumentException {
		this.id = id;
		this.channels = new Hashtable<Integer, Channel>();
		this.users = new Hashtable<Integer, UserData>();
		
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			
			Class.forName("org.mariadb.jdbc.Driver");
			Connection connection = ds.getConnection();
			
			// DB: get server
			PreparedStatement statement = connection.prepareStatement("SELECT id, name, owner_id, created_at FROM Servers WHERE id=? LIMIT 1");
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
				Channel channel = new Channel(this, res.getInt("id"), res.getString("name"));
				channels.put(channel.getChannelID(), channel);
			}
			
			res.close();
			statement.close();
			
			// DB: get server members
			statement = connection.prepareStatement("SELECT server_id, user_id, username FROM Server_Members JOIN Users ON user_id=Users.id WHERE server_id=?");
			statement.setInt(1, id);
			res = statement.executeQuery();
			
			while (res.next()) {
				UserData user = new UserData(res.getInt("user_id"), res.getString("server_id"));
				users.put(user.getID(), user);
			}
			
			res.close();
			statement.close();
			
			connection.close();
			
		} catch (SQLException | NamingException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
