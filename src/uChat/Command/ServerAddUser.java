package uChat.Command;

import java.util.Date;
import uChat.CommandCode;

public class ServerAddUser implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_ADD_USER; }
	
	private int server_id;
	private Date timestamp;
	
	public int getServerID() {
		return server_id;
	}
	public void setServerID(int server_id) {
		this.server_id = server_id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
