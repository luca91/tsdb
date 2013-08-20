package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection conn = null;
	
	public DBConnection() throws Exception {
		//getDBConnection();
	}
	
    public boolean getDBConnection() throws Exception{
		try {
			Class.forName("org.postgresql.Driver");
			String connectionUrl = "jdbc:postgresql://localhost:5432/postgres"; 
			conn = DriverManager.getConnection(connectionUrl, "postgres","admin");
			return true;
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find the database driver");
		} catch (SQLException e) {
			System.out.println("Could not connect to the database");
		}
		return false;
    }
    
    // disconnect from the database
    public void closeDBConnection() throws SQLException{
    	conn.close();
    }
    
    public Connection getConn() {
    	return conn;
    }
    
}
