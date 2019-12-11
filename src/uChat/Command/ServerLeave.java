package uChat.Command;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;
import uChat.Command.ACK.ICommandACK;
import uChat.Command.ACK.Error.UnimplementedErrorACK;

public class ServerLeave implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_LEAVE; }
	
	private int server_id;
	
	public int getServerID() { return server_id; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	
	public static ServerLeave initialize(JsonElement data) {
		return new Gson().fromJson(data, ServerLeave.class);
	}
	
	public ICommandACK execute(User user, UUID session) {
		return new UnimplementedErrorACK(this);
	}
}
