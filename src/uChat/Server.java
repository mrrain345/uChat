package uChat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Server
 */
@WebServlet("/Server")
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Server() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		ServletOutputStream out = response.getOutputStream();
		/*out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\"/>");
		out.println("<title>uChat</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3>Hello World</h3>");
		out.println("</body>");
		out.println("<html>");*/
		
		out.println("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=\"UTF-8\">\n" + 
				"<title>Tomcat WebSocket</title>\n" + 
				"</head>\n" + 
				"<body>\n" + 
				"    <form>\n" + 
				"        <input id=\"message\" type=\"text\">\n" + 
				"        <input onclick=\"wsSendMessage();\" value=\"Echo\" type=\"button\">\n" + 
				"        <input onclick=\"wsCloseConnection();\" value=\"Disconnect\" type=\"button\">\n" + 
				"    </form>\n" + 
				"    <br>\n" + 
				"    <textarea id=\"echoText\" rows=\"5\" cols=\"30\"></textarea>\n" + 
				"    <script type=\"text/javascript\">\n" + 
				"        var webSocket = new WebSocket(\"ws://localhost:8080/uChat/websocketendpoint\");\n" + 
				"        var echoText = document.getElementById(\"echoText\");\n" + 
				"        echoText.value = \"\";\n" + 
				"        var message = document.getElementById(\"message\");\n" + 
				"        webSocket.onopen = function(message){ wsOpen(message);};\n" + 
				"        webSocket.onmessage = function(message){ wsGetMessage(message);};\n" + 
				"        webSocket.onclose = function(message){ wsClose(message);};\n" + 
				"        webSocket.onerror = function(message){ wsError(message);};\n" + 
				"        function wsOpen(message){\n" + 
				"            echoText.value += \"Connected ... \\n\";\n" + 
				"        }\n" + 
				"        function wsSendMessage(){\n" + 
				"            webSocket.send(message.value);\n" + 
				"            echoText.value += \"Message sended to the server : \" + message.value + \"\\n\";\n" + 
				"            message.value = \"\";\n" + 
				"        }\n" + 
				"        function wsCloseConnection(){\n" + 
				"            webSocket.close();\n" + 
				"        }\n" + 
				"        function wsGetMessage(message){\n" + 
				"            echoText.value += \"Message received from to the server : \" + message.data + \"\\n\";\n" + 
				"        }\n" + 
				"        function wsClose(message){\n" + 
				"            echoText.value += \"Disconnect ... \\n\";\n" + 
				"        }\n" + 
				" \n" + 
				"        function wsError(message){\n" + 
				"            echoText.value += \"Error ... \\n\";\n" + 
				"        }\n" + 
				"    </script>\n" + 
				"</body>\n" + 
				"</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
