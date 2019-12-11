package uChat.Json;

import java.io.Serializable;

public class ChannelJson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int channel_id;
	private String channel_name;
	
	public int getChannelID() { return channel_id; }
	public String getChannelName() { return channel_name; }
	
	public void setChannelID(int channel_id) { this.channel_id = channel_id; }
	public void setChannelName(String channel_name) { this.channel_name = channel_name; }
	
	public ChannelJson() {};
	public ChannelJson(int channel_id, String channel_name) {
		this.channel_id = channel_id;
		this.channel_name = channel_name;
	}
}
