let webSocket = null;

function wsSendMessage(message) {
    webSocket.send(message);
    console.log("[WebSocket] Send: " + message);
}

function wsClose() {
    webSocket.close();
    console.log("[WebSocket] Closed");
}

function wsOnOpen(message) {
    console.log("[WebSocket] Connected");
}

function wsOnMessage(message) {
    console.log("[WebSocket] Recieved: " + message.data);
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
    if (result.sessionID !== undefined) wsConnect();
});
