package uChat.Command;

import uChat.CommandCode;

public class ServerCreate implements ICommand {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_CREATE; }
	
	private String server_name;
	
	public String getServerName() {
		return server_name;
	}

	public void setServerName(String server_name) {
		this.server_name = server_name;
	}
}
