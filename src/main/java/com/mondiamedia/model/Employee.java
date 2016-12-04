package com.mondiamedia.model;


//this is model class that represent the DataBase table
public class Employee {
	private int id;
	private String name;
	private String designation;
	private double salary;
/*
 * getters and setters 
 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
//implementing toString method to be used on print employee method
	@Override
	public String toString() {
		return name + ", designation " + designation + ", salary:" + salary;
	}

	
}
