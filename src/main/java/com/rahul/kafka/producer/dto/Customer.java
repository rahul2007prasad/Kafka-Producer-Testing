package com.rahul.kafka.producer.dto;

public class Customer {

	

	private int id;
	private String name;
	private String email;
	private String contactNum;
	public Customer(int id, String name, String email, String contactNum) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.contactNum = contactNum;
	}
	public Customer() {
		super();
		
	}
	


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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	@Override
	public String toString() {
		return "Consumer [id=" + id + ", name=" + name + ", email=" + email + ", contactNum=" + contactNum + "]";
	}
}
