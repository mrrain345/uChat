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
	
	/*
	  var webSocket = new WebSocket("ws://localhost:8080/api");
	  var echoText = document.getElementById("echoText");
	  echoText.value = "";
	  var message = document.getElementById("message");
	  webSocket.onopen = function(message){ wsOpen(message);};
	  webSocket.onmessage = function(message){ wsGetMessage(message);};
	  webSocket.onclose = function(message){ wsClose(message);};
	  webSocket.onerror = function(message){ wsError(message);};
	  function wsOpen(message){
	      echoText.value += "Connected ... \n";
	  }
	  function wsSendMessage(){
	      webSocket.send(message.value);
	      echoText.value += "Message sended to the server : " + message.value + "\n";
	      message.value = "";
	  }
	  function wsCloseConnection(){
	      webSocket.close();
	  }
	  function wsGetMessage(message){
	      echoText.value += "Message received from to the server : " + message.data + "\n";
	  }
	  function wsClose(message){
	      echoText.value += "Disconnect ... \n";
	  }

	  function wsError(message){
	      echoText.value += "Error ... \n";
	  }

	  document.getElementById('echo').addEventListener('click', wsSendMessage);
	  document.getElementById('disconnect').addEventListener('click', wsCloseConnection);
	*/

});