package uChat.Command.ACK;

import java.util.List;

import uChat.CommandCode;
import uChat.Json.MessageJson;

public class ServerSyncACK implements ICommandACK {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_SYNC_ACK; }

	private int server_id;
	private int channel_id;
	private List<MessageJson> messages;
	
	public int getServerID() { return server_id; }
	public int getChannelID() { return channel_id; }
	public List<MessageJson> getMessages() { return messages; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannelID(int channel_id) { this.channel_id = channel_id; }
	public void setMessages(List<MessageJson> messages) { this.messages = messages; }
	
	public ServerSyncACK() {}
	public ServerSyncACK(int server_id, int channel_id, List<MessageJson> messages) {
		this.server_id = server_id;
		this.channel_id = channel_id;
		this.messages = messages;
	}
}
