package uChat.Json;

import java.io.Serializable;
import java.util.UUID;
import com.google.gson.JsonElement;
import uChat.CommandCode;
import uChat.User;
import uChat.Command.*;
import uChat.Command.ACK.ICommandACK;

public class CommandJson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID session_id;
	private int code;
	private JsonElement data;
	
	public void setSessionID(UUID session_id) { this.session_id = session_id; }
	public void setCode(int code) { this.code = code; }
	public void setCode(CommandCode code) { this.code = code.getValue(); }
	public void setData(JsonElement data) { this.data = data; }
	
	public UUID getSessionID() { return session_id; }
	public CommandCode getCode() { return CommandCode.fromValue(code); }
	public JsonElement getData() { return data; }
	
	public ICommandACK execute(User user, UUID session) {
		switch(getCode()) {
		case CHANNEL_CREATE: 		return ChannelCreate.initialize(data).execute(user, session);
		case CHANNEL_DESTROY:		return ChannelDestroy.initialize(data).execute(user, session);
		case CHANNEL_LIST:			return ChannelList.initialize(data).execute(user, session);
		case CHANNEL_MESSAGE:		return ChannelMessage.initialize(data).execute(user, session);
		case CHANNEL_RECEIVE:		return ChannelReceive.initialize(data).execute(user, session);
		case FRIEND_ADD:			return FriendAdd.initialize(data).execute(user, session);
		case FRIEND_LIST:			return FriendList.initialize(data).execute(user, session);
		case FRIEND_MESSAGE:		return FriendMessage.initialize(data).execute(user, session);
		case FRIEND_RECEIVE:		return FriendReceive.initialize(data).execute(user, session);
		case FRIEND_REMOVE:			return FriendRemove.initialize(data).execute(user, session);
		case HEARTBEAT:				return Heartbeat.initialize(data).execute(user, session);
		case ROLE_GET_PERMISSIONS:	return RoleGetPermissions.initialize(data).execute(user, session);
		case ROLE_SET_PERMISSIONS:	return RoleSetPermissions.initialize(data).execute(user, session);
		case SERVER_ADD_USER:		return ServerAddUser.initialize(data).execute(user, session);
		case SERVER_CREATE:			return ServerCreate.initialize(data).execute(user, session);
		case SERVER_DESTROY:		return ServerDestroy.initialize(data).execute(user, session);
		case SERVER_GET_ROLES:		return ServerGetRoles.initialize(data).execute(user, session);
		case SERVER_GET_USERS:		return ServerGetUsers.initialize(data).execute(user, session);
		case SERVER_LEAVE:			return ServerLeave.initialize(data).execute(user, session);
		case SERVER_LIST:			return ServerList.initialize(data).execute(user, session);
		case SERVER_REMOVE_ROLE:	return ServerRemoveRole.initialize(data).execute(user, session);
		case SERVER_SYNC:			return ServerSync.initialize(data).execute(user, session);
		case USER_GET_ROLES:		return UserGetRoles.initialize(data).execute(user, session);
		case USER_SET_ROLES:		return UserSetRoles.initialize(data).execute(user, session);
		
		default:
			System.err.println("Incorrect command code: " + code);
			return ICommandACK.error(CommandCode.UNKNOW, 254, "Incorrect command code: " + code);
		}
	}
}
