package uChat.Command.ACK.Error;

import uChat.CommandCode;
import uChat.Command.ICommand;

public class InternalServerErrorACK extends ErrorACK {
	private static final long serialVersionUID = 1L;
	public static final int STATUS_CODE = 1;
	
	public InternalServerErrorACK() {
		super(CommandCode.UNKNOW, STATUS_CODE, "Internal server error");
	}
	
	public InternalServerErrorACK(CommandCode command) {
		super(command, STATUS_CODE, "Internal server error");
	}
	
	public InternalServerErrorACK(ICommand cmd) {
		super(CommandCode.fromValue(cmd.getCode().getValue()+1), STATUS_CODE, "Internal server error");
	}
}
