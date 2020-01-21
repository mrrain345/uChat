package uChat.Http;

import uChat.*;
import uChat.Json.RegisterJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

@WebServlet("/register/*")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Register() {
        super();
    }
	
	void PrintError(HttpServletResponse response, String message) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("{ \"status\": false, \"error\": \"" + message + "\" }");
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	boolean isValidEmail(String email) {
		String regex = "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$"; 
	    Pattern pattern = Pattern.compile(regex);
	    if (email == null) return false; 
	    return pattern.matcher(email).matches(); 
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
		
		Connection connection = null;
		
		try {
			BufferedReader reader = request.getReader();
			Gson gson = new Gson();
			RegisterJson register = gson.fromJson(reader, RegisterJson.class);
			
			if (register.getUsername().length() < 3) {
				PrintError(response, "Username is too short (min. 3 characters)");
				return;
			}
			
			if (register.getPassword().length() < 8) {
				PrintError(response, "Password is too short (min. 8 characters)");
				return;
			}
			
			if (register.getPassword().compareTo(register.getConfirmPassword()) != 0) {
				PrintError(response, "Passwords don't match");
				return;
			}
			
			if (!isValidEmail(register.getEmail())) {
				PrintError(response, "Incorrect email");
				return;
			}
			
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			
			Class.forName("org.mariadb.jdbc.Driver");
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			// DB: check if username or email exists
			PreparedStatement statement = connection.prepareStatement("SELECT username, email FROM Users WHERE username=? OR email=? LIMIT 1");
			statement.setString(1, register.getUsername());
			statement.setString(2, register.getEmail());
			ResultSet res = statement.executeQuery();
			
			if (res.next()) {
				if (res.getString("username").compareTo(register.getUsername()) == 0) PrintError(response, "Username is not available");
				else PrintError(response, "Email is not available");
				res.close();
				statement.close();
				connection.rollback();
				connection.close();
				return;
			}
			
			res.close();
			statement.close();
			
			// DB: create user
			PreparedStatement statement2 = connection.prepareStatement("INSERT INTO Users (username, password, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement2.setString(1, register.getUsername());
			statement2.setString(2, register.getPassword());
			statement2.setString(3, register.getEmail());
			statement2.execute();
			
			ResultSet res2 = statement2.getGeneratedKeys();
			
			if (!res2.next()) {
				PrintError(response, "Database error");
				res2.close();
				statement2.close();
				connection.rollback();
				connection.close();
				return;
			}
			
			res2.close();
			statement2.close();
			connection.commit();
			connection.close();
			
			System.out.println("User created");
			
			User user = Users.authenticate(register.getUsername(), register.getPassword());
			
			if (user != null) {
				List<UUID> sessions = user.getSessions();
				PrintWriter out = response.getWriter();
				out.println("{ \"success\": true, \"session_id\": \"" + sessions.get(sessions.size()-1) + "\" }");
				System.out.printf("[USER CREATED] username: \"%s\", session: \"%s\"\n", user.getUsername(), sessions.get(sessions.size()-1));
			} else {
				System.out.printf("[Register Authentication failed] username: \"%s\"\n", register.getUsername());
				PrintError(response, "Authentication failed");
			}
		} catch(Exception e) {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.rollback();
					connection.close();
				}
			} catch (SQLException e1) { e1.printStackTrace(); }
			PrintWriter out = response.getWriter();
			out.println("{ \"error\": \"" + e.getMessage() + "\", \"stacktrace\": \"");
			e.printStackTrace(out);
			out.println("\" }");
		}
	}
}
