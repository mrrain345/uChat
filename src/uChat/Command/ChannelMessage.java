package uChat.Command;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;

public class ChannelMessage implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_MESSAGE; }
	
	private int server_id;
	private int channel_id;
	private String message;
	
	public int getServerID() { return server_id; }
	public int getChannelID() { return channel_id; }
	public String getMessage() { return message; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannelID(int channel_id) { this.channel_id = channel_id; }
	public void setMessage(String message) { this.message = message; }
	
	public static ChannelMessage initialize(JsonElement data) {
		return new Gson().fromJson(data, ChannelMessage.class);
	}
	
	public String execute(User user, UUID session) {
		return null;
	}
}
