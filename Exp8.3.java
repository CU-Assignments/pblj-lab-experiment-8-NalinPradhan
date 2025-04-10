Steps to Develop a JSP-Based Student Portal with Attendance Submission
  
1. Set Up the Database (MySQL)
Create a database named StudentDB.
Create a table attendance with columns: id, student_name, date, status.
Insert sample records for testing.
CREATE DATABASE StudentDB;

USE StudentDB;

CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100),
    date DATE,
    status VARCHAR(10)
);

INSERT INTO attendance (student_name, date, status) VALUES
('Amit Sharma', '2025-04-10', 'Present'),
('Riya Sen', '2025-04-10', 'Absent');

2. Configure Your Java Project
Add MySQL JDBC Driver (via Maven or manually).
Set up Apache Tomcat in your IDE (Eclipse/VScode).
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
</dependency>

3. Create a Database Connection Class (DBConnection.java)
This utility class connects to MySQL.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
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

4. Create the JSP Form (attendance.jsp)
A form where users enter student name, date, and attendance status (Present/Absent).
<!DOCTYPE html>
<html>
<head>
    <title>Attendance Form</title>
</head>
<body>
    <h2>Mark Attendance</h2>
    <form action="AttendanceServlet" method="post">
        Student Name: <input type="text" name="student_name" required><br><br>
        Date: <input type="date" name="date" required><br><br>
        Status:
        <select name="status">
            <option value="Present">Present</option>
            <option value="Absent">Absent</option>
        </select><br><br>
        <input type="submit" value="Submit Attendance">
    </form>
</body>
</html>

5. Develop the Servlet (AttendanceServlet.java)
Handles form submission and saves attendance to the database.
Uses JDBC to insert data into MySQL.
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String name = request.getParameter("student_name");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO attendance (student_name, date, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, date);
            stmt.setString(3, status);
            stmt.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h3>Attendance submitted successfully!</h3>");
        } catch (Exception e) {
            throw new ServletException("DB Error: " + e.getMessage());
        }
    }
}

6. Configure web.xml (If Needed)
Map AttendanceServlet to handle form submissions.
<web-app xmlns="http://jakarta.ee/xml/ns/jakartaee" version="5.0">
    <servlet>
        <servlet-name>AttendanceServlet</servlet-name>
        <servlet-class>AttendanceServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>AttendanceServlet</servlet-name>
        <url-pattern>/AttendanceServlet</url-pattern>
    </servlet-mapping>
</web-app>

7. Deploy & Test
Run on Tomcat.
Access via http://localhost:8080/YourAppName/attendance.jsp.
Submit attendance, check database for saved records.

Enhancements (Optional)
- Display submitted attendance records in attendance.jsp.
- Add CSS/Bootstrap for better UI.
- Implement session handling for authentication.
