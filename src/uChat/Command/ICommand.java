package uChat.Command;

import java.io.Serializable;
import uChat.CommandCode;

public interface ICommand extends Serializable {
	public CommandCode getCode();
}
