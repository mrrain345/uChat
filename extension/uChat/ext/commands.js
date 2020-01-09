function CMD_UNKNOW(data) {}
function CMD_SERVER_SYNC_ACK(data) {}
function CMD_SERVER_GET_ROLES_ACK(data) {}

function CMD_SERVER_GET_USERS_ACK(data) {
	servers_set_users(data.server_id, data.users);
}

function CMD_SERVER_ADD_USER_ACK(data) {}
function CMD_ROLE_GET_PERMISSIONS_ACK(data) {}
function CMD_ROLE_SET_PERMISSIONS_ACK(data) {}
function CMD_USER_GET_ROLES_ACK(data) {}
function CMD_USER_SET_ROLES_ACK(data) {}
function CMD_SERVER_DESTROY_ACK(data) {}
function CMD_SERVER_REMOVE_ROLE_ACK(data) {}
function CMD_SERVER_LEAVE_ACK(data) {}
function CMD_CHANNEL_CREATE_ACK(data) {}

function CMD_CHANNEL_LIST_ACK(data) {
	servers_set_channels(data.server_id, data.channels);
}

function CMD_CHANNEL_MESSAGE_ACK(data) {}
function CMD_CHANNEL_RECEIVE_ACK(data) {}
function CMD_CHANNEL_DESTROY_ACK(data) {}

function CMD_SERVER_CREATE_ACK(data) {
	servers_add(data);
	wsCommand('CHANNEL_LIST', { server_id: data.server_id });
	wsCommand('SERVER_GET_USERS', { server_id: data.server_id });
}

function CMD_SERVER_LIST_ACK(data) {
	servers_set(data.servers);
	for (let i = 0; i < data.servers.length; i++) {
		const id = data.servers[i].server_id;
		wsCommand('CHANNEL_LIST', { server_id: id });
		wsCommand('SERVER_GET_USERS', { server_id: id });
	}
}

function CMD_FRIEND_MESSAGE_ACK(data) {}
function CMD_FRIEND_RECEIVE_ACK(data) {}
function CMD_FRIEND_LIST_ACK(data) {}
function CMD_FRIEND_ADD_ACK(data) {}
function CMD_FRIEND_REMOVE_ACK(data) {}
function CMD_HEARTBEAT_ACK(data) {}
function CMD_EVENT_MESSAGE(data) {
	console.log('NEW MESSAGE:', data);
	messagePort.postMessage({ data: data });
}