let webSocket = null;
let wsPort = null;
let messagePort = null;
let sessionID = null;
let messagesData = null;

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
		case 'EVENT_MESSAGE':				return 49;
	}
}

function codeToString(code) {
	switch(code) {
		case  0: return 'UNKNOW';
		case  1: return 'SERVER_SYNC';
		case  2: return 'SERVER_SYNC_ACK';
		case  3: return 'SERVER_GET_ROLES';
		case  4: return 'SERVER_GET_ROLES_ACK';
		case  5: return 'SERVER_GET_USERS';
		case  6: return 'SERVER_GET_USERS_ACK';
		case  7: return 'SERVER_ADD_USER';
		case  8: return 'SERVER_ADD_USER_ACK';
		case  9: return 'ROLE_GET_PERMISSIONS';
		case 10: return 'ROLE_GET_PERMISSIONS_ACK';
		case 11: return 'ROLE_SET_PERMISSIONS';
		case 12: return 'ROLE_SET_PERMISSIONS_ACK';
		case 13: return 'USER_GET_ROLES';
		case 14: return 'USER_GET_ROLES_ACK';
		case 15: return 'USER_SET_ROLES';
		case 16: return 'USER_SET_ROLES_ACK';
		case 17: return 'SERVER_DESTROY';
		case 18: return 'SERVER_DESTROY_ACK';
		case 19: return 'SERVER_REMOVE_ROLE';
		case 20: return 'SERVER_REMOVE_ROLE_ACK';
		case 21: return 'SERVER_LEAVE';
		case 22: return 'SERVER_LEAVE_ACK';
		case 23: return 'CHANNEL_CREATE';
		case 24: return 'CHANNEL_CREATE_ACK';
		case 25: return 'CHANNEL_LIST';
		case 26: return 'CHANNEL_LIST_ACK';
		case 27: return 'CHANNEL_MESSAGE';
		case 28: return 'CHANNEL_MESSAGE_ACK';
		case 29: return 'CHANNEL_RECEIVE';
		case 30: return 'CHANNEL_RECEIVE_ACK';
		case 31: return 'CHANNEL_DESTROY';
		case 32: return 'CHANNEL_DESTROY_ACK';
		case 33: return 'SERVER_CREATE';
		case 34: return 'SERVER_CREATE_ACK';
		case 35: return 'SERVER_LIST';
		case 36: return 'SERVER_LIST_ACK';
		case 37: return 'FRIEND_MESSAGE';
		case 38: return 'FRIEND_MESSAGE_ACK';
		case 39: return 'FRIEND_RECEIVE';
		case 40: return 'FRIEND_RECEIVE_ACK';
		case 41: return 'FRIEND_LIST';
		case 42: return 'FRIEND_LIST_ACK';
		case 43: return 'FRIEND_ADD';
		case 44: return 'FRIEND_ADD_ACK';
		case 45: return 'FRIEND_REMOVE';
		case 46: return 'FRIEND_REMOVE_ACK';
		case 47: return 'HEARTBEAT';
		case 48: return 'HEARTBEAT_ACK';
		case 49: return 'EVENT_MESSAGE';
		default: return 'UNKNOW';
	}
}

function wsSendMessage(command, session, message) {
	if (!session) {
		console.error("["+command+"] You are not logged in. Failed to send:", message);
		return;
	}
	
	const data = {
		code: getCodeID(command),
		session_id: session,
		data: message
	};
	
    webSocket.send(JSON.stringify(data));
    console.log("> ["+command+"]", message);
}

function wsCommand(command, data) {
	console.log(command);
	wsSendMessage(command, sessionID, data);
}

function wsClose() {
    webSocket.close();
    console.log("[WebSocket] Closed");
}

function wsOnOpen(message) {
	console.log("[WebSocket] Connected");
	wsCommand('SERVER_LIST', {});
}

function wsOnMessage(message) {
	const data = JSON.parse(message.data);
    if (data.status === 0) console.log("< ["+codeToString(data.code)+"]", data.data);
    else console.error("["+codeToString(data.code)+"] ("+data.status+")", data.error);
    
    command_callback(data.code, data.data);
}

function wsOnClose(message) {
    console.log("[WebSocket] Disconnected");
    wsConnect();
}

function wsOnError(message) {
    console.error("[WebSocket Error]", message);
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
		sessionID = result.sessionID;
		wsConnect();
	}
});

chrome.runtime.onConnect.addListener(function(port) {
	if (port.name === 'WebSocket') {
		wsPort = port;
		wsPort.onMessage.addListener(function(msg) {
			wsCommand(msg.command, msg.data);
		});
	}
	else if (port.name === 'Servers') {
    	port.onMessage.addListener(function(msg) {
			servers_send(port, msg);
		});
	}
	else if (port.name === 'Login') {
		port.onMessage.addListener(function(msg) {
			if (msg.login) {
				sessionID = msg.session;
				wsConnect();
			} else {
				wsClose();
				sessionID = null;
			}
		});
	}
	else if (port.name === 'Message') {
		messagePort = port;
		messagePort.onMessage.addListener(function(msg) {
			if (msg.command === 'STATE') {
				if (messagesData !== null) {
					messagePort.postMessage({
						command: 'STATE',
						status: true,
						server_id: messagesData.server_id,
						channel_id: messagesData.channel_id,
						messages: messagesData.messages
					});
				} else {
					messagePort.postMessage({
						command: 'STATE',
						status: false
					});
				}
			}
			if (msg.command === 'SYNC') {
				if (messagesData !== null && messagesData.server_id === msg.server_id && messagesData.channel_id === msg.channel_id) {
					messagePort.postMessage({
						command: 'SYNC',
						server_id: messagesData.server_id,
						channel_id: messagesData.channel_id,
						messages: messagesData.messages
					});
				} else {
					messagesData = {
						server_id: msg.server_id,
						channel_id: msg.channel_id,
						messages: null
					}
					wsCommand('SERVER_SYNC', { server_id: msg.server_id, channel_id: msg.channel_id });
				}
			}
		});
	}
	else console.error('Incorrect port:', port.name);
});

