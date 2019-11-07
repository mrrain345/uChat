package uChat;

import java.io.IOException;
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

@WebServlet("/Server")
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Server() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletOutputStream out = response.getOutputStream();
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\"/>");
		out.println("<title>uChat</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3>Hello World</h3>");
		
		try {
		
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/database");
			
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = ds.getConnection();
			Statement db = conn.createStatement();
			
			String sql = "SELECT 1 as id";
			ResultSet res = db.executeQuery(sql);
			
			while(res.next()) {
				int id = res.getInt("id");
				out.println("DB: " + id + "<br/>");
			}
			
			res.close();
			db.close();
			conn.close();
		} catch (NamingException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		out.println("</body>");
		out.println("<html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
