package uChat.Command;

import java.util.Date;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import uChat.CommandCode;
import uChat.User;

public class ServerSync implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_SYNC; }
	
	private int server_id;
	private Date timestamp;
	
	public int getServerID() { return server_id; }
	public Date getTimestamp() { return timestamp; }
	
	public void setServerID(int server_id) { this.server_id = server_id; }
	public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
	
	public static ServerSync initialize(JsonElement data) {
		return new Gson().fromJson(data, ServerSync.class);
	}
	
	public String execute(User user, UUID session) {
		return null;
	}
}
