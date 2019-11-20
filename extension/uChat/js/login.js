$(document).ready(function() {
	chrome.storage.local.get("sessionID", function(result){
	    if (result.sessionID !== undefined) window.location = '/index.html';
	});
	
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
				if (res.login) {
					chrome.storage.local.set({ "sessionID": res.session_id }, function(){
						window.location = '/index.html';
					});
				} else $('#login-alert').text('Bad login or password');
			},
			error: function (res) {
				$('#login-alert').text('Connection failed ;(');
				console.log(res);
			}
		});
		
		e.preventDefault();
	});
});