let uchat_servers = [];
let uchat_servers_refresh = true;
let uchat_server_port = null;

function servers_set(servers) {
	uchat_servers_refresh = true;
	uchat_servers = [];
	for (let i = 0; i < servers.length; i++) {
		const server = servers[i];
		uchat_servers.push({
			id: server.server_id,
			owner_id: server.owner_id,
			name: server.server_name,
			channels: [],
			users: []
		});
	}
}

function servers_add(server) {
	uchat_servers_refresh = true;
	uchat_servers.push({
		id: server.server_id,
		owner_id: server.owner_id,
		name: server.server_name,
		channels: [],
		users: []
	});
}

function servers_set_channels(server_id, channels) {
	uchat_servers_refresh = true;
	const uchat_channels = [];
	for (let i = 0; i < channels.length; i++) {
		const channel = channels[i];
		uchat_channels.push({
			id: channel.channel_id,
			name: channel.channel_name
		});
	}
	
	for (let i = 0; i < uchat_servers.length; i++) {
		if (uchat_servers[i].id !== server_id) continue;
		uchat_servers[i].channels = uchat_channels;
		break;
	}
	servers_refresh();
}

function servers_add_channel(server_id, channel_id, channel_name) {
	uchat_servers_refresh = true;
	for (let i = 0; i < uchat_servers.length; i++) {
		if (uchat_servers[i].id !== server_id) continue;
		uchat_servers[i].channels.push({
			id: channel_id,
			name: channel_name
		});
		break;
	}
	servers_refresh();
}

function servers_set_users(server_id, users) {
	uchat_servers_refresh = true;
	const uchat_users = [];
	for (let i = 0; i < users.length; i++) {
		const user = users[i];
		uchat_users.push({
			id: user.user_id,
			username: user.username
		});
	}
	
	for (let i = 0; i < uchat_servers.length; i++) {
		if (uchat_servers[i].id !== server_id) continue;
		uchat_servers[i].users = uchat_users;
		break;
	}
	servers_refresh();
}

function servers_add_user(server_id, user_id, username) {
  uchat_servers_refresh = true;
  for (let i = 0; i < uchat_servers.length; i++) {
		if (uchat_servers[i].id !== server_id) continue;
		uchat_servers[i].users.push({
			id: user_id,
			username: username
		});
		break;
  }
	servers_refresh();
}

function servers_refresh() {
	if (!uchat_servers_refresh) return;
	if (uchat_server_port === null) return;
	uchat_servers_refresh = false;

	uchat_server_port.postMessage({
		command: 'SERVERS_REFRESH',
		servers: uchat_servers
	});
}

function servers_send(port, msg) {
	uchat_server_port = port;
	if (msg.command === "SERVERS_REFRESH") {
		if (msg.first !== true && !uchat_servers_refresh) return;
		uchat_servers_refresh = false;

		port.postMessage({
			command: 'SERVERS_REFRESH',
			servers: uchat_servers
		});
	}
	else console.error(`[Navbar port] Incorrect command: ${msg}`);
}