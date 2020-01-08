package uChat;

import java.util.UUID;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;

import uChat.Command.ACK.ICommandACK;
import uChat.Json.CommandJson;

@ServerEndpoint("/api")
public class WebSocketServlet {

	@OnOpen
	public void open(Session session) {
		System.out.println("[WebSocket] Open");
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("[WebSocket] Close");
	}
	
	@OnError
	public void onError(Throwable error) {
		System.out.println("[WebSocket] Error");
		error.printStackTrace();
	}
	
	@OnMessage
	public String handleMessage(String message, Session wsSession) {
		Gson gson = new Gson();
		CommandJson cmd = gson.fromJson(message, CommandJson.class);
		UUID session = cmd.getSessionID();
		User user = Users.findUser(session);
		
		if (user == null) {
			System.err.printf("[BAD SESSION] \"%s\"\n", cmd.getSessionID());
			return "{ \"error\":\"Bad session\"}";
		}
		
		user.setWsSession(session, wsSession);
		ICommandACK response = cmd.execute(user, session);
		System.out.printf("[%s] username: \"%s\", session: \"%s\"\n>  %s\n<  %s\n\n", cmd.getCode(), user.getUsername(), cmd.getSessionID(), cmd.getData(), response.getDataJSON());
		return response.toJSON();
	}
}
