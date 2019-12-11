let wsPort = null;

function codeToString(code) {
	switch(code) {
		case  0: return 'UNKNOW';
		case  1: return 'SERVER_SYNC';
		case  2: return 'SERVER_SYNC_ACK';
		case  3: return 'SERVER_GET_ROLES';
		case  4: return 'SERVER_GET_ROLES_ACK';
		case  5: return 'SERVER_GET_USERS';
		case  6: return 'SERVER_GET_USERS_ACK';
		case  7: return 'SERVER_ADD_USER';
		case  8: return 'SERVER_ADD_USER_ACK';
		case  9: return 'ROLE_GET_PERMISSIONS';
		case 10: return 'ROLE_GET_PERMISSIONS_ACK';
		case 11: return 'ROLE_SET_PERMISSIONS';
		case 12: return 'ROLE_SET_PERMISSIONS_ACK';
		case 13: return 'USER_GET_ROLES';
		case 14: return 'USER_GET_ROLES_ACK';
		case 15: return 'USER_SET_ROLES';
		case 16: return 'USER_SET_ROLES_ACK';
		case 17: return 'SERVER_DESTROY';
		case 18: return 'SERVER_DESTROY_ACK';
		case 19: return 'SERVER_REMOVE_ROLE';
		case 20: return 'SERVER_REMOVE_ROLE_ACK';
		case 21: return 'SERVER_LEAVE';
		case 22: return 'SERVER_LEAVE_ACK';
		case 23: return 'CHANNEL_CREATE';
		case 24: return 'CHANNEL_CREATE_ACK';
		case 25: return 'CHANNEL_LIST';
		case 26: return 'CHANNEL_LIST_ACK';
		case 27: return 'CHANNEL_MESSAGE';
		case 28: return 'CHANNEL_MESSAGE_ACK';
		case 29: return 'CHANNEL_RECEIVE';
		case 30: return 'CHANNEL_RECEIVE_ACK';
		case 31: return 'CHANNEL_DESTROY';
		case 32: return 'CHANNEL_DESTROY_ACK';
		case 33: return 'SERVER_CREATE';
		case 34: return 'SERVER_CREATE_ACK';
		case 35: return 'SERVER_LIST';
		case 36: return 'SERVER_LIST_ACK';
		case 37: return 'FRIEND_MESSAGE';
		case 38: return 'FRIEND_MESSAGE_ACK';
		case 39: return 'FRIEND_RECEIVE';
		case 40: return 'FRIEND_RECEIVE_ACK';
		case 41: return 'FRIEND_LIST';
		case 42: return 'FRIEND_LIST_ACK';
		case 43: return 'FRIEND_ADD';
		case 44: return 'FRIEND_ADD_ACK';
		case 45: return 'FRIEND_REMOVE';
		case 46: return 'FRIEND_REMOVE_ACK';
		case 47: return 'HEARTBEAT';
		case 48: return 'HEARTBEAT_ACK';
		default: return 'UNKNOW';
	}
}

function command_callback(code, data) {
	switch(code) {
		case  0: return CMD_UNKNOW(data);
		case  1: return CMD_SERVER_SYNC(data);
		case  2: return CMD_SERVER_SYNC_ACK(data);
		case  3: return CMD_SERVER_GET_ROLES(data);
		case  4: return CMD_SERVER_GET_ROLES_ACK(data);
		case  5: return CMD_SERVER_GET_USERS(data);
		case  6: return CMD_SERVER_GET_USERS_ACK(data);
		case  7: return CMD_SERVER_ADD_USER(data);
		case  8: return CMD_SERVER_ADD_USER_ACK(data);
		case  9: return CMD_ROLE_GET_PERMISSIONS(data);
		case 10: return CMD_ROLE_GET_PERMISSIONS_ACK(data);
		case 11: return CMD_ROLE_SET_PERMISSIONS(data);
		case 12: return CMD_ROLE_SET_PERMISSIONS_ACK(data);
		case 13: return CMD_USER_GET_ROLES(data);
		case 14: return CMD_USER_GET_ROLES_ACK(data);
		case 15: return CMD_USER_SET_ROLES(data);
		case 16: return CMD_USER_SET_ROLES_ACK(data);
		case 17: return CMD_SERVER_DESTROY(data);
		case 18: return CMD_SERVER_DESTROY_ACK(data);
		case 19: return CMD_SERVER_REMOVE_ROLE(data);
		case 20: return CMD_SERVER_REMOVE_ROLE_ACK(data);
		case 21: return CMD_SERVER_LEAVE(data);
		case 22: return CMD_SERVER_LEAVE_ACK(data);
		case 23: return CMD_CHANNEL_CREATE(data);
		case 24: return CMD_CHANNEL_CREATE_ACK(data);
		case 25: return CMD_CHANNEL_LIST(data);
		case 26: return CMD_CHANNEL_LIST_ACK(data);
		case 27: return CMD_CHANNEL_MESSAGE(data);
		case 28: return CMD_CHANNEL_MESSAGE_ACK(data);
		case 29: return CMD_CHANNEL_RECEIVE(data);
		case 30: return CMD_CHANNEL_RECEIVE_ACK(data);
		case 31: return CMD_CHANNEL_DESTROY(data);
		case 32: return CMD_CHANNEL_DESTROY_ACK(data);
		case 33: return CMD_SERVER_CREATE(data);
		case 34: return CMD_SERVER_CREATE_ACK(data);
		case 35: return CMD_SERVER_LIST(data);
		case 36: return CMD_SERVER_LIST_ACK(data);
		case 37: return CMD_FRIEND_MESSAGE(data);
		case 38: return CMD_FRIEND_MESSAGE_ACK(data);
		case 39: return CMD_FRIEND_RECEIVE(data);
		case 40: return CMD_FRIEND_RECEIVE_ACK(data);
		case 41: return CMD_FRIEND_LIST(data);
		case 42: return CMD_FRIEND_LIST_ACK(data);
		case 43: return CMD_FRIEND_ADD(data);
		case 44: return CMD_FRIEND_ADD_ACK(data);
		case 45: return CMD_FRIEND_REMOVE(data);
		case 46: return CMD_FRIEND_REMOVE_ACK(data);
		case 47: return CMD_HEARTBEAT(data);
		case 48: return CMD_HEARTBEAT_ACK(data);
		default: return CMD_UNKDOW(data);
	}
}

function wsCommand(command, data) {
	wsPort.postMessage({
		command: command,
		session: sessionID,
		data: data
	});
}

$(document).ready(function() {
	wsPort = chrome.runtime.connect({name: "WebSocket"});
	wsPort.onMessage.addListener(function(command) {
		if (command.status === 0) command_callback(command.code, command.data);
		else console.error(command);
	});
});