let webSocket = null;
let wsPort = null;

function getCodeID(command) {
	switch(command) {
		case 'UNKNOW':						return  0;
		case 'SERVER_SYNC':					return  1;
		case 'SERVER_SYNC_ACK':				return  2;
		case 'SERVER_GET_ROLES':			return  3;
		case 'SERVER_GET_ROLES_ACK':		return  4;
		case 'SERVER_GET_USERS':			return  5;
		case 'SERVER_GET_USERS_ACK':		return  6;
		case 'SERVER_ADD_USER':				return  7;
		case 'SERVER_ADD_USER_ACK':			return  8;
		case 'ROLE_GET_PERMISSIONS':		return  9;
		case 'ROLE_GET_PERMISSIONS_ACK':	return 10;
		case 'ROLE_SET_PERMISSIONS':		return 11;
		case 'ROLE_SET_PERMISSIONS_ACK':	return 12;
		case 'USER_GET_ROLES':				return 13;
		case 'USER_GET_ROLES_ACK':			return 14;
		case 'USER_SET_ROLES':				return 15;
		case 'USER_SET_ROLES_ACK':			return 16;
		case 'SERVER_DESTROY':				return 17;
		case 'SERVER_DESTROY_ACK':			return 18;
		case 'SERVER_REMOVE_ROLE':			return 19;
		case 'SERVER_REMOVE_ROLE_ACK':		return 20;
		case 'SERVER_LEAVE':				return 21;
		case 'SERVER_LEAVE_ACK':			return 22;
		case 'CHANNEL_CREATE':				return 23;
		case 'CHANNEL_CREATE_ACK':			return 24;
		case 'CHANNEL_LIST':				return 25;
		case 'CHANNEL_LIST_ACK':			return 26;
		case 'CHANNEL_MESSAGE':				return 27;
		case 'CHANNEL_MESSAGE_ACK':			return 28;
		case 'CHANNEL_RECEIVE':				return 29;
		case 'CHANNEL_RECEIVE_ACK':			return 30;
		case 'CHANNEL_DESTROY':				return 31;
		case 'CHANNEL_DESTROY_ACK':			return 32;
		case 'SERVER_CREATE':				return 33;
		case 'SERVER_CREATE_ACK':			return 34;
		case 'SERVER_LIST':					return 35;
		case 'SERVER_LIST_ACK':				return 36;
		case 'FRIEND_MESSAGE':				return 37;
		case 'FRIEND_MESSAGE_ACK':			return 38;
		case 'FRIEND_RECEIVE':				return 39;
		case 'FRIEND_RECEIVE_ACK':			return 40;
		case 'FRIEND_LIST':					return 41;
		case 'FRIEND_LIST_ACK':				return 42;
		case 'FRIEND_ADD':					return 43;
		case 'FRIEND_ADD_ACK':				return 44;
		case 'FRIEND_REMOVE':				return 45;
		case 'FRIEND_REMOVE_ACK':			return 46;
		case 'HEARTBEAT':					return 47;
		case 'HEARTBEAT_ACK':				return 48;
	}
}

function wsSendMessage(command, session, message) {
	console.log(session);
	
	if (!session) {
		console.error("[WebSocket "+command+"] You are not logged in. Failed to send:", message);
		return;
	}
	
	const data = {
		code: getCodeID(command),
		session_id: session,
		data: message
	};
	
    webSocket.send(JSON.stringify(data));
    console.log("[WebSocket "+command+"] Send:", message);
}

function wsClose() {
    webSocket.close();
    console.log("[WebSocket] Closed");
}

function wsOnOpen(message) {
    console.log("[WebSocket] Connected");
}

function wsOnMessage(message) {
    console.log("[WebSocket] Recieved:", message.data);
    if (wsPort) wsPort.postMessage(JSON.parse(message.data));
    else console.error("wsPort is closed");
}

function wsOnClose(message) {
    console.log("[WebSocket] Disconnected");
    wsConnect();
}

function wsOnError(message) {
    console.log("[WebSocket] Error");
    console.log(message);
}

function wsConnect() {
	webSocket = new WebSocket("ws://localhost:8080/api");
	
	webSocket.onopen = wsOnOpen;
	webSocket.onmessage = wsOnMessage;
	webSocket.onclose = wsOnClose;
	webSocket.onerror = wsOnError;
}

chrome.storage.local.get("sessionID", function(result) {
    if (result.sessionID !== undefined) {
    	wsConnect();
    }
});

chrome.runtime.onConnect.addListener(function(port) {
	if (port.name === 'WebSocket') {
		wsPort = port;
		wsPort.onMessage.addListener(function(msg) {
			console.log(msg);
			wsSendMessage(msg.command, msg.session, msg.data);
		});
	}
	else console.error('Incorrect port:', port.name);
});

