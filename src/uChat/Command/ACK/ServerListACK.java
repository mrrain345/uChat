package uChat.Command.ACK;

import java.util.List;

import uChat.CommandCode;
import uChat.Json.ServerJson;

public class ServerListACK implements ICommandACK {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.SERVER_LIST_ACK; }

	private List<ServerJson> servers;
	
	public List<ServerJson> getServers() { return servers; }
	
	public void setServers(List<ServerJson> servers) { this.servers = servers; }
	
	public ServerListACK() {}
	public ServerListACK(List<ServerJson> servers) {
		this.servers = servers;
	}
}
