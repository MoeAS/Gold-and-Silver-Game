package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

//creates a database called "users" which is needed to signup and use the LogIn to have access
//Run this while running WAMP or XAMP before running everything else

public class jdbc {

	static final String Driver = "com.mysql.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost:3306?autoReconnect=true&useSSL=true";
	
	static final String User = "root";
	static final String Pass = "";
	
	public static void main(String[] args) throws Exception{
		Connection conn = null;
		Statement st = null;
		Statement table = null;
		
		Class.forName(Driver);
		
		conn = DriverManager.getConnection(URL, User, Pass);
		
		st = conn.createStatement();
		
		String query = "CREATE DATABASE users";
		st.executeUpdate(query);
		System.out.println("Database created");
		conn = null;
		
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?autoReconnect=true&useSSL=true", User, Pass);
		table = conn.createStatement();
		
		
		String t ="create table users (" + 
				" ID int," + 
				" FirstName text," + 
				" LastName text," +
				" Username varchar(100)," + 
				" Password varchar(40)," +
				" Gender varchar(20)," +
				" LocalDateTime text," +
				" Gold int(11)," + 
				" Silver int(11)," +
				" SingleGames int," +
				" HighScore int," +
				" primary key (ID))";
		table.executeUpdate(t);
		System.out.println("Table created");
		
		
		st.close();
		table.close();
		conn.close();
	}
}
