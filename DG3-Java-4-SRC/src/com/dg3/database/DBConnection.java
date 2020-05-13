package com.dg3.database;
import java.sql.*;
public class DBConnection {
	public static Connection createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dg3-java4", "root", ""); // Make sure you have imported the included database.
			return con; // onConnection return the connection for further use.
		}catch(Exception e) {
			System.out.println(e);
		}
		return null; // If no connection is found, return null
	}
}
