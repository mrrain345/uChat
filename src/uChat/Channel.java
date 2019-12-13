package uChat;

import java.io.Serializable;
import java.util.UUID;

public class Channel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Server server;
	private int channelID;
	private String name;
	
	public Server getServer() { return server; }
	public int getChannelID() { return channelID; }
	public String getName() { return name; }
	
	public void sendMessage(User user, UUID session, String message) {
		// TODO
	}
	
	public Channel() {}
	public Channel(Server server, int channelID, String name) {
		this.server = server;
		this.channelID = channelID;
		this.name = name;
	}
}
