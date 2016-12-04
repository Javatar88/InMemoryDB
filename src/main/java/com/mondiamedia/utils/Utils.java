package com.mondiamedia.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.derby.iapi.util.StringUtil;

import com.mondiamedia.model.Employee;

public class Utils {
	//validate adding new employee before inserting
	public static Employee validateAdd(String query) {
		try{
		Employee addEmp = new Employee();
		List<String> arr = Arrays.asList(query.split("-"));
		//checking the size of the argument  as  "1002‐Ahmed‐IT Manager‐22000" 
		if (arr.size() != 4) {
			System.out.println("invalid inputs");
			
		} else {
			//checking if the employee ID is numric with apache common
			if (NumberUtils.isDigits(arr.get(0).trim())) {
			
				addEmp.setId(Integer.parseInt(arr.get(0).trim()));
			
			} else {
				System.out.println("Invalid Employee Id");
				return null;
			}
			if (StringUtils.isNotBlank(arr.get(1).trim())) {
				addEmp.setName(arr.get(1));
				
			} else {
				System.out.println("Invalid Employee Name");
			}
			if (StringUtils.isNotBlank(arr.get(2).trim())) {
				addEmp.setDesignation(arr.get(2));
			} else {
				System.out.println("Invalid Employee Designation");
			}
			if (NumberUtils.isDigits(arr.get(3).trim())) {
				addEmp.setSalary(Double.parseDouble(arr.get(3)));
			} else {
				System.out.println("Invalid Employee Salary");
			}
		}
		//return employee to be added to the DB
		return addEmp;
		}catch(Exception ex){
		ex.printStackTrace();
		}
		return null;
	}
	//validate delete  user 
	public static Employee validateDelete(String query) {
		Employee deleteEmp = new Employee();
		List<String> arr = Arrays.asList(query.split("-"));
		//checking argument size
		if (arr.size() != 1) {
			System.out.println("please Check your inputs");

		} else {
			if (NumberUtils.isDigits(arr.get(0).trim())) {
				deleteEmp.setId(Integer.parseInt(arr.get(0).trim()));
			} else {
				System.out.println("Invalid Employee Id");
				return null;
			}

		}
		return deleteEmp;
	}
	//valida on update employee  that return hashMap  to detemine the column and Employee object
	public static HashMap<String, Employee> validateUpdate(String query) {
		Employee updateEmp = new Employee();
		//initialize array of argument from the input 
		List<String> arr = Arrays.asList(query.split("-"));
		//initialize the hashMap to be filled it with column name and employee to be updated 
		HashMap<String, Employee> empMap = new HashMap<>();
		//validate user inserted argument 
		if (arr.size() != 3) {
			System.out.println("please Enter Valid input");
		} else {
			if (NumberUtils.isDigits(arr.get(0).trim())) {
				updateEmp.setId(Integer.parseInt(arr.get(0).trim()));
			} else {
				System.out.println("Invalid Employee Id");
				return null;
			}
			//check the column to be updated based on user input
			if (StringUtils.isNotBlank(arr.get(1).trim())) {
				if (arr.get(1).trim().equalsIgnoreCase("SALARY")) {
					updateEmp.setSalary(Double.parseDouble(arr.get(2)));
					empMap.put("emp_salary", updateEmp);
					return empMap;
				} else {
					if (StringUtils.isNotBlank(arr.get(1).trim())) {
						if (arr.get(1).trim().toUpperCase().equalsIgnoreCase("NAME")) {
							updateEmp.setName((arr.get(2)));
							empMap.put("emp_name", updateEmp);
							return empMap;
						}
					} else {
						if (StringUtils.isNotBlank(arr.get(1).trim())) {
							if (arr.get(1).trim().toUpperCase().equalsIgnoreCase("DESIGNATION")) {
								updateEmp.setName((arr.get(2)));
								empMap.put("emp_designation", updateEmp);
								return empMap;
							}
						}

					}
				}
			}
		}
		return null;
	}
}