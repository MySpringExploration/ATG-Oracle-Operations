package org.iqra.operationsapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Customer")
public class Customer {

	@Id
	@Column(name="custId")
	int custId;
	String name;
	int age;
	
	public Customer(int custId, String name, int age) {
		super();
		this.custId = custId;
		this.name = name;
		this.age = age;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
