function CMD_UNKNOW(data) {
  console.error(data);
}

function CMD_SERVER_SYNC_ACK(data) {
	if (data.server_id !== messagesData.server_id || data.channel_id !== messagesData.channel_id) return;
	messagesData.messages = data.messages;

	messagePort.postMessage({
		command: 'SYNC',
		server_id: messagesData.server_id,
		channel_id: messagesData.channel_id,
		messages: messagesData.messages
	});
}

function CMD_SERVER_GET_ROLES_ACK(data) {}

function CMD_SERVER_GET_USERS_ACK(data) {
	servers_set_users(data.server_id, data.users);
}

function CMD_SERVER_ADD_USER_ACK(data) {
  if (!data.success) {
    if (uchat_server_port === null) return;

    uchat_server_port.postMessage({
      command: 'USER_ADD_FAIL',
      username: data.username
    });
  }
}

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
	console.log('EVENT_MESSAGE:', data);
	if (data.server_id !== messagesData.server_id || data.channel_id !== messagesData.channel_id) return;
	if (messagesData.messages !== null) messagesData.messages.push(data);
	messagePort.postMessage({ command: 'NEW_MESSAGE', data: data });
}

function CMD_EVENT_CHANNEL_CREATED(data) {
  servers_add_channel(data.server_id, data.channel_id, data.channel_name);
}

function CMD_EVENT_CHANNEL_REMOVED(data) {}

function CMD_EVENT_USER_ADDED(data) {
  for (let i = 0; i < uchat_servers.length; i++) {
    if (uchat_servers[i].id === data.server_id) {
      servers_add_user(data.server_id, data.user_id, data.username);
      return;
    }
  }

  servers_add(data.server_id);
}

function CMD_EVENT_USER_REMOVED(data) {}