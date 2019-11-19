package uChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

@WebServlet("/Server")
public class Server extends HttpServlet {
	
    public Server() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "application/json");
		PrintWriter out = response.getWriter();
		out.println("{ \"message\": \"Hello world!\" }");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "application/json");
		
		try {
			BufferedReader reader = request.getReader();
			Gson gson = new Gson();
			LoginJson login = gson.fromJson(reader, LoginJson.class);
			User user = User.authenticate(login);
			
			if (user != null) System.out.println(user + ": " + user.getSessionID());
			else System.out.println("Authentication failed, " + login.getUsername() + " " + login.getPassword());
			
			ServletOutputStream out = response.getOutputStream();
			if (user == null) out.println("{ \"login\": false }");
			else out.println("{ \"login\": true, \"session_id\": \"" + user.getSessionID() + "\" }");
			
		} catch(Exception e) {
			PrintWriter out = response.getWriter();
			out.println("{ \"error\": \"" + e.getMessage() + "\", \"stacktrace\": \"");
			e.printStackTrace(out);
			out.println("\" }");
		}
	}

}
