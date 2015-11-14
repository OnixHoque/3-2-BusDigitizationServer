
import java.sql.*;

public class DatabaseClass {
	
	static String url = "jdbc:mysql://localhost:3306/busdb";
	static String username = "java";
	static String password = "password";
	static Connection connection;
	/**
	 * Launch the application.
	 */
	static boolean ConnectDB()
	{
		/*
		System.out.println("Loading driver...");

		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}*/
		try 
		{
			connection = DriverManager.getConnection(url, username, password);
			//System.out.println("Database connected!");
			if (connection.isClosed())
				return false;
			else
				return true;
		}
		catch (SQLException e)
		{
			//throw new IllegalStateException("Cannot connect the database!", e);
			//System.out.println("Cannot connect to the database!");
			return false;
		}
		
		
	}
	
	/**
	 * Create a new user account
	 **/
	static boolean New_cred(String usr, String pass1)
	{
		Statement stmt;
		ResultSet rs;
		String pass = PasswordSecurity.Encrypt(pass1);
		String col_name = "INSERT_USER('"+usr+"', '"+pass+"')";
		String query = "Select "+ col_name;
		//rs = ParseQ(query);
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next())
		      {
		        int output = rs.getInt(col_name);
		        if (output == 1)
		        	//System.out.println("Signup Success");
		        	return true;
		        else
		        	return false;
		        	//System.out.println("User already exists");
		      }
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Check if username and password is valid
	 **/
	static boolean Check_cred(String usr, String pass1)
	{
		Statement stmt;
		ResultSet rs;
		String pass = PasswordSecurity.Encrypt(pass1);
		String col_name = "VERIFY_DATA('"+usr+"', '"+pass+"')";
		String query = "Select "+ col_name;
		//rs = ParseQ(query);
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next())
		      {
		        int output = rs.getInt(col_name);
		        if (output == 1)
		        	//System.out.println("Login Success");
		        	return true;
		        else
		        	return false;
		        	//System.out.println("Invalid Login");
		      }
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	static String[] get_locations()
	{
		String[] s = {"Shahbagh", "Malibagh", "Motijheel"};
		return s;
	}
	
	static boolean User_Exists(String usr)
	{
		Statement stmt;
		ResultSet rs;
		String query = "Select SEARCH_USER('" + usr + "');";
		//rs = ParseQ(query);
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next())
		      {
		        int output = rs.getInt("SEARCH_USER('" + usr + "')");
		        if (output == 1)
		        	//System.out.println("User found");
		        	return true;
		        else
		        	return false;
		        	//System.out.println("User Not found");
		      }
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/*private static ResultSet ParseQ(String s)
	{
		Statement stmt;
		ResultSet rs = null;
		String query = s;
		try {
		stmt = connection.createStatement();
		rs = stmt.executeQuery(query);
	    stmt.close();
	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}*/
	/**
	 * 
	 * @param password
	 * @return
	 */

	static void closeDB()
	{
		if (connection == null) return;
		try {
			if (connection.isClosed()) return;	
			connection.close();
			//System.out.println("Database closed!");
		} 
		catch (NullPointerException e1)
		{
			//connection is not even established
			return;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
