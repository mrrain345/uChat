package uChat.Http;

import uChat.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
		if (request.getRequestURI().split("/").length != 2) { response.setStatus(404); return; }
		
		PrintWriter out = response.getWriter();
		out.println("{ \"message\": \"Hello world!\" }");
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
				System.out.println(user);
				List<UUID> sessions = user.getSessions();
				out.println("{ \"login\": true, \"session_id\": \"" + sessions.get(sessions.size()-1) + "\" }");
			} else {
				System.out.println("Authentication failed, " + login.getUsername() + " " + login.getPassword());
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
			if (user != null) user.logout(session);
			else System.out.println("User doesn't exist");
		} catch (IllegalArgumentException e) {
			response.setStatus(400);
		}
	}
}
