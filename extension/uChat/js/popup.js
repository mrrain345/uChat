$(document).ready(function() {
	$('#login-form').submit(function(e) {
		let username = $('#username').val();
		let password = $('#password').val();
		
		$.ajax({
			type: 'POST',
			url: 'http://localhost:8080/login',
			contentType: 'application/json',
			crossDomain: true,
			cache: false,
			dataType: 'json',
			data: JSON.stringify({
				username: username,
				password: password
			}),
			success: function (res) {
				console.log('done');
				console.log(res);
			},
			error: function (res) {
				console.log('fail');
				console.log(res);
			}
		});
		
		e.preventDefault();
	});
});

/*
  var webSocket = new WebSocket("ws://localhost:8080/uChat/api");
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
