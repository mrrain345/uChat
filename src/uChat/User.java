package uChat;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.websocket.Session;

import com.google.gson.Gson;

import uChat.Json.MessageJson;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private List<UUID> sessions;
	private HashMap<UUID, Session> wsSessions;
	
	public User() {
		sessions = new ArrayList<UUID>();
		wsSessions = new HashMap<UUID, Session>();
	}
	
	public int getID() { return id; }
	public String getUsername() { return username; }
	
	public void setID(int id) { this.id = id; }
	public void setUsername(String username) { this.username = username; }
	
	public List<UUID> getSessions() { return sessions; }
	public void setSessions(List<UUID> sessions) { this.sessions = sessions; }
	public UUID getSession(int index) { return sessions.get(index); }
	public void setSession(int index, UUID session, Session wsSession) { this.sessions.set(index, session); this.wsSessions.replace(session, wsSession); }
	public void setWsSession(UUID session, Session wsSession) { this.wsSessions.put(session, wsSession); }
	public void addSession(UUID session, Session wsSession) { this.sessions.add(session); this.wsSessions.put(session, wsSession); }
	public void addSession(UUID session) { this.sessions.add(session); }
	public boolean hasSession(UUID session) { return this.sessions.contains(session); }
	
	public boolean isOnline() { return sessions.size() != 0; }
	
	public boolean removeSession(UUID session) {
		wsSessions.remove(session);
		return sessions.remove(session);
	}
	
	public boolean removeSession(Session wsSession) {
		for (int i = 0; i < sessions.size(); i++) {
			UUID session = sessions.get(i);
			if (wsSessions.containsKey(session)) {
				wsSessions.remove(session);
				sessions.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public void sendMessage(MessageJson message) {
		String msg = String.format(
			"{\"code\":%d,\"status\":0,\"data\":%s}",
			CommandCode.EVENT_MESSAGE.getValue(),
			new Gson().toJson(message)
		);
		sendWsMessage(msg);
	}
	
	public void sendWsMessage(UUID session, String message) {
		Session wsSession = wsSessions.get(session);
		try {
			wsSession.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendWsMessage(String message) {
		for (UUID session : sessions) {
			Session wsSession = wsSessions.get(session);
		//for (Session wsSession : wsSessions.values()) {
			System.out.printf("EVENT: MESSAGE '%s' %s\n", getUsername(), session.toString());
			try {
				wsSession.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
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
