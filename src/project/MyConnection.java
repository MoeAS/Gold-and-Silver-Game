package project;

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MyConnection {
	private static String servername="localhost"; 
	private static String username="root"; 
	private static String dbname="users"; 
	private static String password=""; 
	private static int portnumber=3306; 
	
	/**
	 * Creates a connection with the game's database
	 */
	public static Connection getConnection() {
		Connection con = null;
		
		MysqlDataSource ds = new MysqlDataSource();
		
		ds.setServerName(servername);	//connecting to mysql database specified
		ds.setUser(username);
		ds.setPassword(password);
		ds.setDatabaseName(dbname);
		ds.setPortNumber(portnumber);
		ds.setUseSSL(true);				//using SSL certificate
		
		
		try {
			con = ds.getConnection();
		} catch (SQLException e) {e.printStackTrace();}
		
		return con;
	}

}
