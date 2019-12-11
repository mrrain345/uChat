let sessionID = null;

chrome.storage.local.get("sessionID", function(result) {
    if (result.sessionID === undefined) window.location = '/login.html';
    else sessionID = result.sessionID;
});

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
	
	$('#server-create-btn').click(function() {
		let name = window.prompt("Server name:");
		if (name !== '') wsCommand('SERVER_CREATE', { server_name: name });
	});
	
	wsCommand('SERVER_LIST', {});
});