let wsPort = null;
let loginPort = chrome.runtime.connect({name: "Login"});

chrome.storage.local.get("sessionID", function(result) {
    if (result.sessionID === undefined) window.location = '/login.html';
});

function wsCommand(command, data) {
	if (wsPort === null) {
		wsPort = chrome.runtime.connect({name: "WebSocket"});
		wsPort.onMessage.addListener(function(command) {
			if (command.status === 0) command_callback(command.code, command.data);
			else console.error(command);
		});
  	}
  
	wsPort.postMessage({
		command: command,
		data: data
	});
}

$(document).ready(function() {
	$('#logout-btn').click(function() {
		chrome.storage.local.get("sessionID", function(result) {
		    let session = result.sessionID;
		    
		    $.ajax({
				type: 'DELETE',
				url: 'http://localhost:8080/login/' + session,
				contentType: 'application/json',
				crossDomain: true,
				cache: false
			});
		    
		    chrome.storage.local.remove("sessionID", function() {
				window.location = '/login.html';
				loginPort.postMessage({ login: false });
			});
		});
	});
	$('#message-box textarea').on('change keyup paste propertychange', function() {
		this.style.height = '5px';
		if (this.scrollHeight <= 200) {
			this.style.height = (this.scrollHeight)+'px';
			this.style['overflow-y'] = 'hidden';
		} else {
			this.style.height = '200px';
			this.style['overflow-y'] = 'scroll';
		}
	});
});