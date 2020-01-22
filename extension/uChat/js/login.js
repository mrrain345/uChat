$(document).ready(function() {
	chrome.storage.local.get("sessionID", function(result) {
	    if (result.sessionID !== undefined) window.location = '/index.html';
	});
	
	$('#login-form').submit(function(e) {
    let loginPort = chrome.runtime.connect({name: "Login"});

		let username = $('#username').val();
		let password = $('#password').val();
		
		$.ajax({
			type: 'POST',
			url: `${SERVER_URL}/login`,
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
            loginPort.postMessage({ login: true, session: res.session_id });
            window.location = '/index.html'
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
  
  $('#register-btn').click(function(e) {
    window.location = '/register.html';
    e.preventDefault();
  });

  $('#login-btn').click(function(e) {
    window.location = "/login.html";
    e.preventDefault();
  });

  $('#register-form').submit(function(e) {
    let loginPort = chrome.runtime.connect({name: "Login"});
		let username = $('#username').val();
    let password = $('#password').val();
    let confirm_password = $('#confirm_password').val();
    let email = $('#email').val();
		
		$.ajax({
			type: 'POST',
			url: `${SERVER_URL}/register`,
			contentType: 'application/json',
			crossDomain: true,
			cache: false,
			dataType: 'json',
			data: JSON.stringify({
				username: username,
        password: password,
        confirm_password: confirm_password,
        email: email
			}),
			success: function (res) {
				if (res.success) {
					chrome.storage.local.set({ "sessionID": res.session_id }, function(){
            loginPort.postMessage({ login: true, session: res.session_id });
            window.location = '/index.html';
					});
				} else $('#register-alert').text(res.error);
			},
			error: function (res) {
				$('#register-alert').text('Connection failed ;(');
				console.log(res);
			}
		});
		
		e.preventDefault();
  });
});