Steps to Implement
1. Set Up Your Environment
- Install Java Development Kit (JDK)
- Install Apache Tomcat (Servlet Container)
- Set up an IDE (Eclipse, IntelliJ, or VScode)

2. Create an HTML Login Form (login.html)
This form collects the username and password from the user.
  <!DOCTYPE html>
<html>
<head>
  <title>Login Page</title>
</head>
<body>
  <h2>Login</h2>
  <form action="LoginServlet" method="post">
    <label>Username:</label>
    <input type="text" name="username" required /><br><br>
    <label>Password:</label>
    <input type="password" name="password" required /><br><br>
    <input type="submit" value="Login" />
  </form>
</body>
</html>


3. Create the Java Servlet to Process Login (LoginServlet.java)
This servlet reads username and password from the request.
It checks the credentials.
- If valid, it displays a welcome message.
- If invalid, it redirects back to the login page.
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Dummy validation
        if ("admin".equals(username) && "1234".equals(password)) {
            out.println("<h2>Welcome, " + username + "!</h2>");
        } else {
            response.sendRedirect("login.html");
        }
    }
}

4.  Configure web.xml
  <web-app xmlns="http://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://jakarta.ee/xml/ns/jakartaee http://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0">

    <servlet>
        <servlet-name>LoginServlet</servlet-name>
        <servlet-class>LoginServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>LoginServlet</servlet-name>
        <url-pattern>/LoginServlet</url-pattern>
    </servlet-mapping>

    <welcome-file-list>
        <welcome-file>login.html</welcome-file>
    </welcome-file-list>
</web-app>

5. Deploy and Run
- Place the login.html file inside the WebContent (for VScode) or webapp (for Maven projects).
- Compile and deploy the servlet in Tomcat.
Access the form in your browser:
http://localhost:8080/login.html
