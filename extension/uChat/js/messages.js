let messagePort = null;
let channel_messages = [];

function message_to_html(message) {
	return `
<div id="message-${message.id}" class="channel-message">
	<span class="message-username">${get_username(message.sender_id)}:</span>
	<span class="message-content">${message.message}</span>
</div>`
}

function get_username(user_id) {
	if (nav_selected.server === null) return null;
	const users = nav_servers.find(s => s.id === nav_selected.server).users;
	return users.find(u => u.id === user_id).username;
}

function message_render() {
	if (channel_messages === null) {
		$('#channel-messages').html('');
		return;
	}

	let html = '';
	for (let i = 0; i < channel_messages.length; i++) {
		const message = channel_messages[i];
		html = message_to_html(message) + html;
	}

	$('#channel-messages').html(html);
}

function messages_sync(server_id, channel_id) {
	channel_messages = [];
	messagePort.postMessage({
		command: 'SYNC',
		server_id: server_id,
		channel_id: channel_id
	});
}

function messages_initialize() {
	messagePort = chrome.runtime.connect({name: "Message"});
	messagePort.onMessage.addListener(function(data) {
		if (data.command === 'STATE') {
			console.log('STATE:', data);
			if (data.status) {
				nav_select(data.server_id, data.channel_id);
				channel_messages = data.messages;
				message_render();
			}
		}
		if (data.command === 'SYNC') {
			if (data.server_id !== nav_selected.server || data.channel_id !== nav_selected.channel) return;
			channel_messages = data.messages;
			message_render();
		}
		else if (data.command === 'NEW_MESSAGE') {
			const message = data.data;

			if (message.server_id === nav_selected.server && message.channel_id === nav_selected.channel) {
				/*const msg = {
					id: message.id,
					sender_id: message.sender_id,
					message: message.message
				};*/
				channel_messages.push(message);
				const html = message_to_html(message);
				$('#channel-messages').prepend(html);
			}
		}
	});
	
	messagePort.postMessage({ command: 'STATE' });

	$('#message-send').click(function() {
		const msg = $('#message-box textarea').val();

		// TODO
		//channel_messages.push({ id: 0, user_id: 1, username: 'MrRaiN', message: msg });
		if (nav_selected.server !== null) {
			wsCommand('CHANNEL_MESSAGE', {
				server_id: nav_selected.server,
				channel_id: nav_selected.channel,
				message: msg
			});
		}

		$('#message-box textarea').val('');
		$('#message-box textarea').css('height', '31px');
		$('#message-box textarea').css('overflow-y', 'hidden');
		message_render();
	});
}