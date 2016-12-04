package com.mondiamedia.memory;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.mondiamedia.connection.ConnectionManager;
import com.mondiamedia.connection.SchemaDefintion;
import com.mondiamedia.dao.EmployeeDAO;
import com.mondiamedia.dao.EmployeeDAOImpl;
import com.mondiamedia.model.Employee;

public class DataBase {

	public static void main(String[] args) {
		//create Employees Table at first run
		SchemaDefintion.createEmployeesTable();

		// getting command line inputs
		while (true) {
			System.out.println("please Enter one of the supported function add,del,update,print,printall,Quit");
			//getting user inputs methodType  to apply such as add,Delete  and the value for provided methodType
			Scanner sc = new Scanner(System.in);
			String methodType = sc.next();
			String value = sc.nextLine();

			EmployeeDAO EmpolyeeDao = new EmployeeDAOImpl();
			//switch statement to decide which method to run
			switch (methodType) {
			case "add":
				EmpolyeeDao.add(value);
				break;
			case "del":
				EmpolyeeDao.delete(value);
				break;
			case "update":
				EmpolyeeDao.update(value);
				break;
			case "print":
				EmpolyeeDao.getEmployeeById(value);
				break;
			case "printall":
				EmpolyeeDao.findAll(value);
				break;
			case "Quit":
				ConnectionManager.closeConnection();
				break;
			default:
				System.out.println("Invalid Argument");
			}
		}

	}

}
