package com.mondiamedia.connection;

import java.sql.Connection;
import java.sql.Statement;

import com.mondiamedia.dao.EmployeeDAOImpl;

public class SchemaDefintion {

	//create the employee table ,it will be called at first in the main method
	public static void createEmployeesTable() {
		Statement stmt;
		try {
			Connection conn = ConnectionManager.getSingleInstance()
					.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(EmployeeDAOImpl.CREATETABLE);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
