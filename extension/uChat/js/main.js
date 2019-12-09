let wsPort = null;
let sessionID = null;

function wsCommand(command, data) {
	wsPort.postMessage({
		command: command,
		session: sessionID,
		data: data
	});
}

chrome.storage.local.get("sessionID", function(result) {
    if (result.sessionID === undefined) window.location = '/login.html';
    else sessionID = result.sessionID;
});

$(document).ready(function() {
	wsPort = chrome.runtime.connect({name: "WebSocket"});
	wsPort.onMessage.addListener(function(message) {
		console.log(message);
	});
	
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
	
	$('#server-create-btn').click(function() {
		let name = window.prompt("Server name:");
		wsCommand('SERVER_CREATE', { server_name: name });
	});
});