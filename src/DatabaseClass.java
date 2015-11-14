
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	static Queue<String> get_routes(String start, String end)
	{
		Queue<String> Q = new LinkedList<String>();
		
		Statement stmt;
		ResultSet rs;
		String query = "CALL FIND_BUS('"+start+"','"+end+"');";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next())
		      {
		        /*String busName = rs.getString("BUSNAME");
		        String rating = rs.getString("RATING");
		        String time = rs.getString("TIM");
		        String distance = rs.getString("DISTANCE");
		        String price = rs.getString("PRICE");
		        String hazard = "0";*/
				Q.add(rs.getString("BUSNAME"));
				Q.add(rs.getString("RATING"));
				Q.add("10");//Q.add(rs.getString("TIM"));
				Q.add(rs.getString("DISTANCE"));
				Q.add(rs.getString("PRICE"));
				Q.add("1");
		        //System.out.println(busName + " "+ rating + " "+ time + " "+ distance + " "+ price + " "+ hazard);
		      }
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Count " + Q.size()/6);
		return Q;
	}
	static String[] get_locations()
	{
		List<String> loc_list = new ArrayList<String>();
		Statement stmt;
		ResultSet rs;
		String query = "Select NAME from junction;";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next())
		      {
		        String output = rs.getString("NAME");
		        if (output.length() != 0) loc_list.add(output);
		      }
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] s = loc_list.toArray(new String[0]);
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
