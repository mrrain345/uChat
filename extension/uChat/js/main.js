$(document).ready(function() {
	chrome.storage.local.get("sessionID", function(result) {
	    if (result.sessionID === undefined) window.location = '/login.html';
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
});