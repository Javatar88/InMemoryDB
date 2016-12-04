package com.mondiamedia.connection;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mondiamedia.dao.EmployeeDAOImpl;

//Singlton connection manager class to initiate and terminate connection 
public class ConnectionManager {
	 private static ConnectionManager dbIsntance;
	private static Properties prop = new Properties();
	private static Connection connection = null;
	
	//private constructor as it is singlton class
	private ConnectionManager() {
		// private constructor //
	}
	//initiate connection if it is not exist
	public static ConnectionManager getSingleInstance(){
	    if(dbIsntance==null){
	        dbIsntance= new ConnectionManager();
	    }
		return dbIsntance;
	    }
// initiate in-memory DB connection
	public  Connection getConnection() {

		try {
			prop.load(getClass().getResourceAsStream("/dbConfig.properties"));
				
			
			Class.forName(prop.getProperty("driverName"));
			connection = DriverManager.getConnection(prop.getProperty("url"));
		
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return connection;
	}
	//close connection and terminate the session
	public static void closeConnection() {
		if (connection == null) {
			System.out.println("No connection found");
		} else {
			try {
				connection.close();
				connection = null;
				System.out.println("Session will be terminated");
				System.exit(0);
			} catch (Exception  ex) {
				System.out.println("Failed to close the connection");
			}
		}
	}
}
