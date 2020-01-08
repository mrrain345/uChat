package uChat.Json;

import java.io.Serializable;

public class MessageJson implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int server_id;
	private int channel_id;
	private int sender_id;
	private String message;
	
	public int getID() { return id; }
	public int getServerID() { return server_id; }
	public int getChannelID() { return channel_id; }
	public int getSenderID() { return sender_id; }
	public String getMessage() { return message; }
	
	public void setID(int id) { this.id = id; }
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannelID(int channel_id) { this.channel_id = channel_id; }
	public void setSenderID(int sender_id) { this.sender_id = sender_id; }
	public void setMessage(String message) { this.message = message; }
	
	public MessageJson() {}
	public MessageJson(int id, int server_id, int channel_id, int sender_id, String message) {
		this.id = id;
		this.server_id = server_id;
		this.channel_id = channel_id;
		this.sender_id = sender_id;
		this.message = message;
	}
}
