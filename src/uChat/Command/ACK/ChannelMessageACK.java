package uChat.Command.ACK;

import uChat.CommandCode;

public class ChannelMessageACK implements ICommandACK{
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_MESSAGE_ACK; }
	
	private int server_id;
	private int channel_id;
	private int message_id;
	private String message;

	public int getServerID() { return server_id; }
	public int getChannelID() { return channel_id; }
	public int getMessageID() { return message_id; }
	public String getMessage() { return message; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannelID(int channel_id) { this.channel_id = channel_id; }
	public void setMessageID(int message_id) { this.message_id = message_id; }
	public void setMessage(String message) { this.message = message; }
	
	public ChannelMessageACK() {}
	public ChannelMessageACK(int server_id, int channel_id, int message_id, String message) {
		this.server_id = server_id;
		this.channel_id = channel_id;
		this.message_id = message_id;
		this.message = message;
	}
}