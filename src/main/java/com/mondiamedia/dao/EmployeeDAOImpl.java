package com.mondiamedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NumberUtils;

import com.mondiamedia.connection.ConnectionManager;
import com.mondiamedia.model.Employee;
import com.mondiamedia.utils.Utils;

public class EmployeeDAOImpl implements EmployeeDAO {

	public static final String CREATETABLE = "CREATE TABLE EMPLOYEES (emp_id INT NOT NULL, emp_name VARCHAR(50) NOT NULL, emp_designation VARCHAR(50) , emp_salary INT )";
	private static final String FIND_BY_ID = "SELECT * FROM EMPLOYEES WHERE emp_id=?";
	private static final String FIND_ALL = "SELECT * FROM EMPLOYEES ORDER BY emp_name";
	private static final String INSERT = "INSERT INTO EMPLOYEES (emp_id, emp_name, emp_designation, emp_salary) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_NAME = "UPDATE EMPLOYEES SET emp_name=?  WHERE emp_id=?";
	private static final String UPDATE_SALARY = "UPDATE EMPLOYEES SET  emp_salary=? WHERE emp_id=?";
	private static final String UPDATE_DESIGNATION = "UPDATE EMPLOYEES SET emp_designation=? WHERE emp_id=?";
	private static final String DELETE = "delete  FROM EMPLOYEES where  emp_id=?";
	private static final String COUNT = "SELECT COUNT(*) AS rowcount from  EMPLOYEES";
	private final Connection connection = ConnectionManager.getSingleInstance()
			.getConnection();

	// add new employee
	@Override
	public void add(String query) {

		try {
			// validate the employee argument
			// note:checking if Employee is exist before insert should be
			// implemented to be more effective

			Employee emp = Utils.validateAdd(query);

			System.out.println();
			PreparedStatement ps = connection.prepareStatement(INSERT);
			ps.setInt(1, emp.getId());
			ps.setString(2, emp.getName());
			ps.setString(3, emp.getDesignation());
			ps.setDouble(4, (emp.getSalary()));
			ps.executeUpdate();
			ps.close();
			System.out.println("Employee '" + emp.getName()
					+ "' added successfully. Total no of employees = "
					+ count());
		} catch (SQLException ex) {
			System.out.println("Please Check your Inputs");
		} catch (Exception ex) {
			System.out.println("Error,Adding Employee didn`t completed ");
		}

	}

	// delete Employee based on employee ID
	@Override
	public void delete(String query) {

		try {
			// validate Input Argument whether it is numric or not
			Employee emp = Utils.validateDelete(query);
			PreparedStatement ps = connection.prepareStatement(DELETE);

			ps.setInt(1, emp.getId());
			int rowsdeleted = ps.executeUpdate();
			if (rowsdeleted == 0) {
				System.out.println("Employee'" + emp.getId() + "'not found");
			} else {
				System.out.println("Employee '" + emp.getId()
						+ "' deleted successfully");
			}

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// updated Employee Data such as (employee name,employee salary,employee
	// Designation)
	@Override
	public void update(String query) {
		PreparedStatement ps;
		Employee emp =new Employee();
		try {
			// validate employee data and return the Employee in hash map to
			// decide which column to update
			Map<String, Employee> empMap = Utils.validateUpdate(query);
			String updateValue = (String) empMap.keySet().toArray()[0];
			 emp = (Employee) empMap.values().toArray()[0];
			// switch statement to decide the affected column to be updated
			switch (updateValue) {
			case "emp_salary":
				ps = connection.prepareStatement(UPDATE_SALARY);
				ps.setDouble(1, emp.getSalary());
				ps.setInt(2, emp.getId());
				ps.executeUpdate();
				ps.close();
				break;
			case "emp_name":
				ps = connection.prepareStatement(UPDATE_NAME);
				ps.setString(1, emp.getName());
				ps.setInt(2, emp.getId());
				ps.executeUpdate();
				ps.close();
				break;
			case "emp_designation":
				ps = connection.prepareStatement(UPDATE_DESIGNATION);
				ps.setString(1, emp.getDesignation());
				ps.setInt(2, emp.getId());
				ps.executeUpdate();
				ps.close();
				break;
			default:
				System.out.println("Invalid Iput");
				break;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("Employee '" + emp.getId() + "'updated.");

	}

	// get specific employee based on ID
	@Override
	public Employee getEmployeeById(String query) {
		Employee findEmp = new Employee();
		try {
			// validate employee Id if it number or not
			if (NumberUtils.isDigits(query.trim())) {
				findEmp.setId(Integer.parseInt(query.trim()));
				PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);
				ps.setInt(1, findEmp.getId());
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					findEmp.setId(rs.getInt("emp_id"));
					findEmp.setName(rs.getString("emp_name"));
					findEmp.setDesignation(rs.getString("emp_designation"));
					findEmp.setSalary(rs.getDouble("emp_salary"));
					System.out.println(findEmp);
					rs.close();
					ps.close();
				} else {
					System.out.println("Employee with Id :'" + findEmp.getId()
							+ "' Is not Found");
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return findEmp;
	}

	// print all employees that have been added
	@Override
	public List<Employee> findAll(String order) {
		List<Employee> employees = new ArrayList<Employee>();
		;
		try {
			// check if the order value is valid
			if ("ASC".equalsIgnoreCase(order.trim())
					|| "DESC".equalsIgnoreCase(order.trim())) {

				Employee emp = null;

				Statement stmt = connection.createStatement();
				// find all employees concatenated with the order type
				ResultSet rs = stmt.executeQuery(FIND_ALL + " " + order);
				emp = new Employee();
				while (rs.next()) {
					emp = new Employee();
					emp.setId(rs.getInt("emp_id"));
					emp.setName(rs.getString("emp_name"));
					emp.setDesignation(rs.getString("emp_designation"));
					emp.setSalary(rs.getDouble("emp_salary"));
					employees.add(emp);
				}
				for (Employee employee : employees) {
					System.out.println(employee.getName() + " :"
							+ employee.getSalary());
				}
				rs.close();
				stmt.close();
			} else {
				System.out.println("Invalid Argument");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return employees;
	}

	// count method to get the value after adding a new employee
	@Override
	public int count() {
		try {

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(COUNT);
			rs.next();
			// row cuont is an alias column to get the count value
			int count = rs.getInt("rowcount");
			return count;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

}
