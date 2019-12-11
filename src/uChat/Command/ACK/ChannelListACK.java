package uChat.Command.ACK;

import java.util.List;

import uChat.CommandCode;
import uChat.Json.ChannelJson;

public class ChannelListACK implements ICommandACK {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_LIST_ACK; }

	private int server_id;
	private List<ChannelJson> channels;
	
	public int getServerID() { return server_id; }
	public List<ChannelJson> getChannels() { return channels; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannels(List<ChannelJson> channels) { this.channels = channels; }
	
	public ChannelListACK() {}
	public ChannelListACK(int server_id, List<ChannelJson> channels) {
		this.server_id = server_id;
		this.channels = channels;
	}
}
