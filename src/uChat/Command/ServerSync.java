package uChat.Command;

import java.io.Serializable;
import java.util.Date;

public class ServerSync implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
