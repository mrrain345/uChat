package uChat;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private List<UUID> sessions;
	
	public User() {
		sessions = new ArrayList<UUID>();
	}
	
	public int getID() { return id; }
	public String getUsername() { return username; }
	
	public void setID(int id) { this.id = id; }
	public void setUsername(String username) { this.username = username; }
	
	public List<UUID> getSessions() { return sessions; }
	public void setSessions(List<UUID> sessions) { this.sessions = sessions; }
	public UUID getSession(int index) { return sessions.get(index); }
	public void setSession(int index, UUID session) { this.sessions.set(index, session); }
	public void addSession(UUID session) { this.sessions.add(session); }
	public boolean removeSession(UUID session) { return this.sessions.remove(session); }
	public boolean hasSession(UUID session) { return this.sessions.contains(session); }
	
	public boolean isOnline() { return sessions.size() != 0; }
	
	public void logout(UUID session) {
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			
			Class.forName("org.mariadb.jdbc.Driver");
			Connection connection = ds.getConnection();
			
			// DB: delete session
			PreparedStatement statement = connection.prepareStatement("DELETE FROM Sessions WHERE sess_id=? LIMIT 1");
			statement.setString(1, session.toString());
			statement.execute();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		removeSession(session);
		//if (getSessions().size() == 0) Users.removeUser(this);
	}
	
	@Override
	public String toString() {
		return getUsername();
	}
}
