package uChat.Command;

import java.io.Serializable;
import java.util.UUID;

import uChat.CommandCode;
import uChat.User;

public interface ICommand extends Serializable {
	public CommandCode getCode();
	public String execute(User user, UUID session);
}
