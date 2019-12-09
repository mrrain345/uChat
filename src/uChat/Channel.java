package uChat;

import java.io.Serializable;

public class Channel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int serverID;
	private int channelID;
	private String name;
	
	public int getServerID() { return serverID; }
	public int getChannelID() { return channelID; }
	public String getName() { return name; }
	
	public Channel() {}
	public Channel(int serverID, int channelID, String name) {
		this.serverID = serverID;
		this.channelID = channelID;
		this.name = name;
	}
}
