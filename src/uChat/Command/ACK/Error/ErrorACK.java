package uChat.Command.ACK.Error;

import uChat.CommandCode;
import uChat.Command.ACK.ICommandACK;

public class ErrorACK implements ICommandACK {
	private static final long serialVersionUID = 1L;
	public CommandCode getCode() { return CommandCode.UNKNOW; }

	private CommandCode command;
	private int status;
	private String error;
	
	public CommandCode getCommand() { return command; }
	public int getStatus() { return status; }
	public String getError() { return error; }
	
	public void setCommand(CommandCode command) { this.command = command; }
	public void setStatus(int status) { this.status = status; }
	public void setError(String error) { this.error = error; }
	
	public ErrorACK() {}
	public ErrorACK(CommandCode command, int status, String error) {
		this.command = command;
		this.status = status;
		this.error = error;
	}
	
	@Override
	public String toJSON() {
		return String.format(
			"{\"code\":%d,\"status\":%d,\"error\":\"%s\"}",
			command.getValue(),
			status,
			error
		);
	}
}
