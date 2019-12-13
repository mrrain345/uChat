let sessionID = null;
let wsPort = null;

chrome.storage.local.get("sessionID", function(result) {
    if (result.sessionID === undefined) window.location = '/login.html';
    else sessionID = result.sessionID;
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
		session: sessionID,
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
			});
		});
	});
});