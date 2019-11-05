package uChat;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/api")
public class WebSocketServlet {

	@OnOpen
	public void open() {
		System.out.println("WebSocket open");
	}
	
	@OnClose
	public void close() {
		System.out.println("WebSocket close");
	}
	
	@OnError
	public void onError(Throwable error) {
		System.out.println("WebSocket error");
		error.printStackTrace();
	}
	
	@OnMessage
	public String handleMessage(String message) {
		System.out.println("WebSocket message: " + message);
		return message;
	}
}   
