package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/university?useTimezone=true&serverTimezone=UTC";
		String name = "root";
		String pass = "";
		
		Connection connection = DriverManager.getConnection(url,name,pass); 
		return connection;
	}
	
}
