package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
	public static Connection getConnection() {
		final String user = "root";
		final String password = "Nhatoi1234";
		final String url = "jdbc:mysql://localhost:3306/java";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		if (JDBCConnection.getConnection() != null) {
			System.out.println("Thành công!");
		} else {
			System.out.println("Thất bại!");
		}
		
	}

}
