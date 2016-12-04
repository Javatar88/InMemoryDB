package com.mondiamedia.dao;

import java.util.List;

import com.mondiamedia.model.Employee;

public interface EmployeeDAO {
	//Data Access Object to be implemented in EmployeeDAOImpl
	
	public void add(String query);
	
	public int count();
	
	public void delete(String query);

	public void update(String query);
	
	public Employee getEmployeeById(String query);

	public List<Employee> findAll(String oder);

	
}
