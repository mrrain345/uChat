package uChat.Command;

import uChat.CommandCode;

public class ChannelDestroy implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_DESTROY; }
	
	private int server_id;
	private int channel_id;
	
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
}
