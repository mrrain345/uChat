package uChat;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;
import uChat.Json.CommandJson;

@ServerEndpoint("/api")
public class WebSocketServlet {

	@OnOpen
	public void open() {
		System.out.println("[WebSocket] Open");
	}
	
	@OnClose
	public void close() {
		System.out.println("[WebSocket] Close");
	}
	
	@OnError
	public void onError(Throwable error) {
		System.out.println("[WebSocket] Error");
		error.printStackTrace();
	}
	
	@OnMessage
	public String handleMessage(String message) {
		System.out.println("[WebSocket] Message: " + message);
		
		Gson gson = new Gson();
		CommandJson cmd = gson.fromJson(message, CommandJson.class);
		
		System.out.println(cmd.getSessionID());
		System.out.println(cmd.getCode());
		System.out.println(cmd.getData());
		
		
		return message;
	}
}
