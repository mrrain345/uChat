package uChat.Http;

import uChat.*;
import uChat.Json.LoginJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/login/*")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "application/json");
		response.setStatus(404);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "application/json");
		if (request.getRequestURI().split("/").length != 2) { response.setStatus(404); return; }
		
		try {
			BufferedReader reader = request.getReader();
			Gson gson = new Gson();
			LoginJson login = gson.fromJson(reader, LoginJson.class);
			User user = Users.authenticate(login);
			
			PrintWriter out = response.getWriter();
			
			if (user != null) {
				List<UUID> sessions = user.getSessions();
				out.println("{ \"login\": true, \"session_id\": \"" + sessions.get(sessions.size()-1) + "\" }");
				System.out.printf("[SESSION CREATED] username: \"%s\", session: \"%s\"\n", user.getUsername(), sessions.get(sessions.size()-1));
			} else {
				System.out.printf("[Authentication failed] username: \"%s\"\n", login.getUsername());
				out.println("{ \"login\": false }");
			}
		} catch(Exception e) {
			PrintWriter out = response.getWriter();
			out.println("{ \"error\": \"" + e.getMessage() + "\", \"stacktrace\": \"");
			e.printStackTrace(out);
			out.println("\" }");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "application/json");
		if (request.getRequestURI().split("/").length != 3) { response.setStatus(404); return; }
		
		String[] uri = request.getRequestURI().split("/");
		try {
			UUID session = UUID.fromString(uri[2]);
			User user = Users.findUser(session);
			if (user != null) {
				user.logout(session);
				System.out.printf("[SESSION CLOSED] username: \"%s\", session: \"%s\"\n", user.getUsername(), session);
			}
		} catch (IllegalArgumentException e) {
			response.setStatus(400);
		}
	}
}
