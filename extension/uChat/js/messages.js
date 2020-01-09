let messagePort = null;

let channel_messages = [
	{ id: 1, user_id: 1, username: 'MrRaiN', message: "Hello world!" },
	{ id: 2, user_id: 2, username: 'M0onek', message: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus vitae pulvinar purus. Nulla sed diam diam. Maecenas sit amet consectetur nunc, in faucibus lacus. Sed scelerisque nisl vel libero posuere tempor. Phasellus semper dapibus dictum. Quisque lacinia iaculis mauris, vitae condimentum lectus." },
	{ id: 3, user_id: 1, username: 'MrRaiN', message: "Hello uChat!" },
	{ id: 4, user_id: 2, username: 'M0onek', message: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus vitae pulvinar purus. Nulla sed diam diam. Maecenas sit amet consectetur nunc, in faucibus lacus. Sed scelerisque nisl vel libero posuere tempor. Phasellus semper dapibus dictum. Quisque lacinia iaculis mauris, vitae condimentum lectus." },
	{ id: 5, user_id: 2, username: 'M0onek', message: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus vitae pulvinar purus. Nulla sed diam diam. Maecenas sit amet consectetur nunc, in faucibus lacus. Sed scelerisque nisl vel libero posuere tempor. Phasellus semper dapibus dictum. Quisque lacinia iaculis mauris, vitae condimentum lectus." }
];

function message_to_html(message) {
	return `
<div id="message-${message.id}" class="channel-message">
	<span class="message-username">${message.username}:</span>
	<span class="message-content">${message.message}</span>
</div>`
}

function message_render() {
	let html = '';
	for (let i = 0; i < channel_messages.length; i++) {
		const message = channel_messages[i];
		html = message_to_html(message) + html;
	}

	$('#channel-messages').html(html);
}

$(document).ready(function () {
	messagePort = chrome.runtime.connect({name: "Message"});
	messagePort.onMessage.addListener(function(data) {
		const message = data.data;
		console.log('NEW MESSAGE!!!', message);

		if (message.server_id === nav_selected.server && message.channel_id === nav_selected.channel) {
			const server = nav_servers.find(s => s.id === message.server_id);
			const username = server.users.find(u => u.id === message.sender_id).username;
			const msg = {
				id: message.id,
				username: username,
				message: message.message
			};
			channel_messages.push(msg);
			const html = message_to_html(msg);
			$('#channel-messages').prepend(html);
		}
	});
	
	message_render();

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
});