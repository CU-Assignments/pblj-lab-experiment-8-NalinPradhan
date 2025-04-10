Steps to Create a JDBC-Integrated Servlet for Employee Management
1. Set Up Database
Create a MySQL database (EmployeeDB).
Create an employees table with columns (id, name, department, salary).
Insert sample employee data.
CREATE DATABASE EmployeeDB;

USE EmployeeDB;

CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    department VARCHAR(100),
    salary DOUBLE
);

INSERT INTO employees VALUES 
(1, 'John Doe', 'Engineering', 75000),
(2, 'Jane Smith', 'Marketing', 65000),
(3, 'Alice Johnson', 'Finance', 80000);

2. Set Up Your Java Project
Add MySQL JDBC Driver (via Maven or manually).
Configure Apache Tomcat in your IDE.
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <version>8.0.33</version>
</dependency>

3. Create Database Connection Class
Write a utility class (DBConnection.java) to establish a connection with the MySQL database.
import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/EmployeeDB";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

4. Develop the Servlet (EmployeeServlet.java)
- Handle GET requests to fetch all employees or search by Employee ID.
- Use JDBC to query data and display it in HTML format.
- Implement a search form for filtering employees by ID.
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String idParam = request.getParameter("id");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt;
            if (idParam != null && !idParam.isEmpty()) {
                stmt = conn.prepareStatement("SELECT * FROM employees WHERE id = ?");
                stmt.setInt(1, Integer.parseInt(idParam));
            } else {
                stmt = conn.prepareStatement("SELECT * FROM employees");
            }

            ResultSet rs = stmt.executeQuery();
            out.println("<html><body>");
            out.println("<form action='EmployeeServlet' method='get'>");
            out.println("Search by ID: <input type='text' name='id' /> <input type='submit' value='Search'/>");
            out.println("</form><br><table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th></tr>");

            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + 
                            rs.getString("name") + "</td><td>" + 
                            rs.getString("department") + "</td><td>" + 
                            rs.getDouble("salary") + "</td></tr>");
            }

            out.println("</table></body></html>");
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}

<web-app xmlns="http://jakarta.ee/xml/ns/jakartaee" version="5.0">
    <servlet>
        <servlet-name>EmployeeServlet</servlet-name>
        <servlet-class>EmployeeServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>EmployeeServlet</servlet-name>
        <url-pattern>/EmployeeServlet</url-pattern>
    </servlet-mapping>
</web-app>

5. Deploy and Run
Deploy the servlet on Tomcat.
Access it via http://localhost:8080/YourAppName/EmployeeServlet.
(The page displays employee records and allows searching by ID.)

Note : Enhancements (Optional)
Improve UI with CSS & Bootstrap.


