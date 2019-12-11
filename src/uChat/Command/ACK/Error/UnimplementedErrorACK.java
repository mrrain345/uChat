package uChat.Command.ACK.Error;

import uChat.CommandCode;
import uChat.Command.ICommand;

public class UnimplementedErrorACK extends ErrorACK {
	private static final long serialVersionUID = 1L;
	public static final int STATUS_CODE = 255;
	
	public UnimplementedErrorACK() {
		super(CommandCode.UNKNOW, STATUS_CODE, "Command 'UNKNOW' is not implemented");
	}
	
	public UnimplementedErrorACK(CommandCode command) {
		super(command, STATUS_CODE, "Command '"+CommandCode.fromValue(command.getValue()-1)+"' is not implemented");
	}
	
	public UnimplementedErrorACK(ICommand cmd) {
		super(CommandCode.fromValue(cmd.getCode().getValue()+1), STATUS_CODE, "Command '"+cmd.getCode()+"' is not implemented");
	}
}
