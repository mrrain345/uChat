package uChat.Json;

import java.io.Serializable;
import java.util.UUID;

import com.google.gson.JsonElement;

import uChat.CommandCode;

public class CommandJson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID session_id;
	private int code;
	private JsonElement data;
	
	public void setSessionID(UUID session_id) { this.session_id = session_id; }
	public void setCode(int code) { this.code = code; }
	public void setCode(CommandCode code) { this.code = code.getValue(); }
	public void setData(JsonElement data) { this.data = data; }
	
	public UUID getSessionID() { return session_id; }
	public CommandCode getCode() { return CommandCode.fromValue(code); }
	public JsonElement getData() { return data; }
}
