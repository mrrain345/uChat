package uChat.Command.ACK;

import uChat.CommandCode;

public class ChannelCreateACK implements ICommandACK{
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_CREATE_ACK; }
	
	private int server_id;
	private int channel_id;
	private String channel_name;

	public int getServerID() { return server_id; }
	public int getChannelID() { return channel_id; }
	public String getChannelName() { return channel_name; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannelID(int channel_id) { this.channel_id = channel_id; }
	public void setChannelName(String channel_name) { this.channel_name = channel_name; }
	
	public ChannelCreateACK() {}
	public ChannelCreateACK(int server_id, int channel_id, String name) {
		this.server_id = server_id;
		this.channel_id = channel_id;
		this.channel_name = name;
	}
}
