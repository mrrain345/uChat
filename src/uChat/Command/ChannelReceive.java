package uChat.Command;

import uChat.CommandCode;

public class ChannelReceive implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_RECEIVE; }
	
	private int server_id;
	private int channel_id;
	private int sender_id;
	private String message;
	
	public int getServerID() {
		return server_id;
	}
	public void setServerID(int server_id) {
		this.server_id = server_id;
	}
	public int getChannelID() {
		return channel_id;
	}
	public void setChannelID(int channel_id) {
		this.channel_id = channel_id;
	}
	public int getSenderID() {
		return sender_id;
	}
	public void setSenderID(int sender_id) {
		this.sender_id = sender_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
