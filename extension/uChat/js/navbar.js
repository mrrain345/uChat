let nav_servers = [];
let nav_selected = { server: null, channel: 0 }
let nav_serverPort = null;

function nav_update_members() {
	let users = [];
	for (let i = 0; i < nav_servers.length; i++) {
		const server = nav_servers[i];
		if (server.id === nav_selected.server) {
			users = server.users;
			break;
		}
	}

	let html = '';
	for (let i = 0; i < users.length; i++) {
		const user = users[i];
		html = `${html}<li>${user.username}</li>`
	}

	$('#server-members-list').html(html);
}

function nav_select(server, channel) {
	console.log('change select:', server, channel);
	if (nav_selected.server !== null) {
		$(`#nav-server-${nav_selected.server}-channel-${nav_selected.channel}`).removeClass('active');
		if (nav_selected.server !== server) {
			$(`#nav-server-${nav_selected.server} .dropdown-menu`).dropdown('hide');
			$(`#nav-server-${nav_selected.server}`).addClass('show');
		}
	}

	$(`#nav-server-${server}-channel-${channel}`).addClass('active');
	$(`#nav-server-${server}`).addClass('show');
	nav_selected.server = server;
	nav_selected.channel = channel;
	nav_update_members()
}

function nav_render() {
	let servers_html = '';
	
	for (var i = 0; i < nav_servers.length; i++) {
		const server = nav_servers[i];
		let channels_html = '';
		
		for (var j = 0; j < server.channels.length; j++) {
			const channel = server.channels[j];
			channels_html = `${channels_html}<span class="dropdown-item nav-channel" id="nav-server-${server.id}-channel-${channel.id}">${channel.name}</span>`;
		}
		
		servers_html = `${servers_html}<li class="nav-item dropdown nav-server" id="nav-server-${server.id}">
			<span class="nav-link dropdown-toggle">${server.name}</span>
			<div class="dropdown-menu">${channels_html}</div>
		</li>`;
	}	
	
	const html = `<ul class="navbar-nav"><li class="nav-item active"><span class="nav-link">Servers:<button id="server-create-btn" class="btn btn-dark float-right">+</button></span></li>${servers_html}</ul>`;
	$('#server-list').html(html);

	$('.nav-server').click(function(e) {
		const target = e.currentTarget;
		$('.nav-server .dropdown-menu').dropdown('hide');
		$(`#nav-server-${nav_selected.server}-channel-${nav_selected.channel}`).dropdown('show');
		$(target).find('.dropdown-menu').dropdown('show');
		$(`.nav-server`).removeClass('show');
		if (nav_selected.server !== null) $(`#nav-server-${nav_selected.server}`).addClass('show');
	});

	$('.nav-channel').click(function(e) {
		const split = $(e.currentTarget).attr('id').split('-');
		nav_select(parseInt(split[2]), parseInt(split[4]));
	});

	$('#server-create-btn').click(function() {
		let name = window.prompt("Server name:");
		if (name !== null && name !== '') wsCommand('SERVER_CREATE', { server_name: name });
	});
}

$(document).ready(function() {
	let nav_first = true;
	nav_serverPort = chrome.runtime.connect({name: "Servers"});
	nav_serverPort.onMessage.addListener(function(data) {
		if (data.command === 'SERVERS_REFRESH') {
			nav_servers = data.servers;
			nav_render();
			if (nav_selected.server !== null) {
				nav_select(nav_selected.server, nav_selected.channel);
				$(`#nav-server-${nav_selected.server}-channel-${nav_selected.channel}`).dropdown('show');
				$(`#nav-server-${nav_selected.server}`).addClass('show');
			}
			console.log(data);
		}
	});

	$('button[data-target="#navbar-nav"]').click(function() {
		if (nav_selected.server !== null) {
			$('.nav-server .dropdown-menu').dropdown('hide');
			$(`.nav-server`).removeClass('show');
			nav_select(nav_selected.server, nav_selected.channel);
			$(`#nav-server-${nav_selected.server}-channel-${nav_selected.channel}`).dropdown('show');
			$(`#nav-server-${nav_selected.server}`).addClass('show');
		}
	});

	nav_serverPort.postMessage({
		command: 'SERVERS_REFRESH',
		first: nav_first
	});
	nav_first = false;
});