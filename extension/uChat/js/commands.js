function CMD_UNKNOW(data) {}
function CMD_SERVER_SYNC_ACK(data) {}
function CMD_SERVER_GET_ROLES_ACK(data) {}
function CMD_SERVER_GET_USERS_ACK(data) {}
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
	let list = '';
	for (let i = 0; i < data.channels.length; i++) {
		list = `${list}<li>${data.channels[i].channel_name}</li>`;
	}
	$(`#channels-list-${data.server_id}`).html(list);
	console.log(`#channels-list-${data.server_id}`);
}

function CMD_CHANNEL_MESSAGE_ACK(data) {}
function CMD_CHANNEL_RECEIVE_ACK(data) {}
function CMD_CHANNEL_DESTROY_ACK(data) {}

function CMD_SERVER_CREATE_ACK(data) {
	let item = `
<li>
	<button id="server-list-${data.server_id}">${data.server_name}</button>
	<ol id="channels-list-${data.server_id}"></ol>
</li>`;
	$('#servers-list').append(item);
	$(`#server-list-${data.server_id}`).click(function() {
		wsCommand('CHANNEL_LIST', { server_id: data.server_id });
	});
}

function CMD_SERVER_LIST_ACK(data) {
	let list = '';
	for (let i = 0; i < data.servers.length; i++) {
		const server = data.servers[i];
		list = `${list}
<li>
	<button id="server-list-${server.server_id}">${server.server_name}</button>
	<ol id="channels-list-${server.server_id}"></ol>
</li>`;
	}
	$('#servers-list').html(list);
	$('#servers-list button').click(function() {
		const id = parseInt($(this).get(0).id.split('-')[2]);
		wsCommand('CHANNEL_LIST', { server_id: id });
	});
}

function CMD_FRIEND_MESSAGE_ACK(data) {}
function CMD_FRIEND_RECEIVE_ACK(data) {}
function CMD_FRIEND_LIST_ACK(data) {}
function CMD_FRIEND_ADD_ACK(data) {}
function CMD_FRIEND_REMOVE_ACK(data) {}
function CMD_HEARTBEAT_ACK(data) {}