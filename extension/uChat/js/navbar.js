let nav_servers = [];
let nav_selected = { server: null, channel: 0 }
let nav_serverPort = null;
const slideDuration = 200;
const menuDuration = 300;

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
		html = `${html}<div>${user.username}</div>`
	}

	$('#server-members-list').html(html);
}

function nav_select(server, channel) {
	nav_selected.server = server;
	nav_selected.channel = channel;
	nav_update_members();
	messages_sync(server, channel);

	$('.nav-server').removeClass('active show');
	$('.nav-channel').removeClass('active');

	$(`#nav-server-${server}`).addClass('active');
	$(`#nav-server-${server}-channel-${channel}`).addClass('active');
	$(`#nav-server-${server} .nav-channels`).slideDown(slideDuration);

	$('.nav-server:not(.active)').each(function() {
		$(this).find('.nav-channels').slideUp(slideDuration);
		$(this).removeClass('show');
	});
}

function navbar_setup() {
	$('.nav-server').click(function(e) {
		if ($(this).hasClass('active')) return;

		if (!$(this).hasClass('show')) {
			$('.nav-server:not(.active)').each(function() {
				$(this).find('.nav-channels').slideUp(slideDuration);
				$(this).removeClass('show');
			});
			$(this).addClass('show');
			$(this).find('.nav-channels').slideDown(slideDuration);
		} else {
			$(this).removeClass('show');
			$(this).find('.nav-channels').slideUp(slideDuration);
		}
	});

	$('.nav-channel').click(function(e) {
		const split = $(e.currentTarget).attr('id').split('-');
		nav_select(parseInt(split[2]), parseInt(split[4]));
		$('#menu-btn').removeClass('nav-active');
		$('#server-list').slideUp(menuDuration);
		$('#server-list').removeClass('active');
	});
}

function nav_render() {
	let servers_html = '';
	
	for (var i = 0; i < nav_servers.length; i++) {
		const server = nav_servers[i];
		let channels_html = '';
		
		for (var j = 0; j < server.channels.length; j++) {
			const channel = server.channels[j];
			channels_html = `${channels_html}<div class="nav-channel" id="nav-server-${server.id}-channel-${channel.id}">${channel.name}</div>`;
		}

		servers_html = `${servers_html}
		<li class="nav-item nav-server" id="nav-server-${server.id}">
			<span class="nav-server-name">${server.name}</span>
			<button class="btn btn-dark float-right nav-add-btn" title="Create channel"><i class="material-icons">add</i></button>
			<div class="nav-channels">${channels_html}</div>
		</li>`
	}

	$('#server-list-data').html(servers_html);
	navbar_setup();
}

$(document).ready(function() {
	nav_serverPort = chrome.runtime.connect({name: "Servers"});
	nav_serverPort.onMessage.addListener(function(data) {
		if (data.command === 'SERVERS_REFRESH') {
			nav_servers = data.servers;
			if (nav_selected.server === null && nav_servers.length > 0) {
				nav_select(nav_servers[0].id, nav_servers[0].channels[0].id);
			}

			nav_render();
			if (nav_selected.server !== null) {
				nav_select(nav_selected.server, nav_selected.channel);
				$(`#nav-server-${nav_selected.server}-channel-${nav_selected.channel}`).dropdown('show');
				$(`#nav-server-${nav_selected.server}`).addClass('show');
			}
		}
	});

	$('#menu-btn').click(function(e) {
		$(this).toggleClass('nav-active');
		if ($(this).hasClass('nav-active')) {
			$('#server-list').slideDown(menuDuration);
			$('#server-list').addClass('active');
		} else {
			$('#server-list').slideUp(menuDuration);
			$('#server-list').removeClass('active');
		}
	});

	$('#server-create-btn').click(function() {
		let name = window.prompt("Server name:");
		if (name !== null && name !== '') wsCommand('SERVER_CREATE', { server_name: name });
	});

	nav_serverPort.postMessage({
		command: 'SERVERS_REFRESH',
		first: true
	});

	messages_initialize();
});