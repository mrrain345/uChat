package uChat.Command;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.Error.UnimplementedErrorACK;
import uChat.Json.Role;

public class ChannelCreate implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.CHANNEL_CREATE; }
	
	private int server_id;
	private String channel_name;
	private Role[] roles;
	
	public int getServerID() { return server_id; }
	public String getChannelName() { return channel_name; }
	public Role[] getRoles() { return roles; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setChannelName(String channel_name) { this.channel_name = channel_name; }
	public void setRoles(Role[] roles) { this.roles = roles; }
	
	public static ChannelCreate initialize(JsonElement data) {
		return new Gson().fromJson(data, ChannelCreate.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		return new UnimplementedErrorACK(this);
	}
}
