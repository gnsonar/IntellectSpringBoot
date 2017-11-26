package com.intellect.user.model;

public class User {
	
	private String id;
	private String fName;
	private String lName;
	private String email;
	private int pinCode;
	private String birthDate;
	private boolean isActive;
	
	public User() {}
	public User(String id, String fname , String lname , String email , int pin, String birthDate) {
		this.id = id;
		this.fName = fname;
		this.lName = lname;
		this.email = email;
		this.pinCode = pin;
		this.birthDate = birthDate;
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPinCode() {
		return pinCode;
	}
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
