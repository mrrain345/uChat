package uChat.Command;

import java.io.Serializable;
import java.util.UUID;

import uChat.CommandCode;
import uChat.User;
import uChat.Command.ACK.ICommandACK;

public interface ICommand extends Serializable {
	public CommandCode getCode();
	public ICommandACK execute(User user, UUID session);
}
