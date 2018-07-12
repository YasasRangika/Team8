package com.team8.controllers;

public class UserDto {
	
	private String self;
	private String username;
	private int phonenumber;
	private String password;
	private String email;
	private String licenseUrl;
	private String userID;
	private String dob;
	private String dateOfIssueLicense;
	private String dateOfExpireLicense;
	private String nic;
	private String address;
	private String imageOfDriverUrl;

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDateOfIssueLicense() {
		return dateOfIssueLicense;
	}

	public void setDateOfIssueLicense(String dateOfIssueLicense) {
		this.dateOfIssueLicense = dateOfIssueLicense;
	}

	public String getDateOfExpireLicense() {
		return dateOfExpireLicense;
	}

	public void setDateOfExpireLicense(String dateOfExpireLicense) {
		this.dateOfExpireLicense = dateOfExpireLicense;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImageOfDriverUrl() {
		return imageOfDriverUrl;
	}

	public void setImageOfDriverUrl(String imageOfDriverUrl) {
		this.imageOfDriverUrl = imageOfDriverUrl;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	public String getSelf() {
		return self;
	}
	
	public void setSelf(String self) {
		this.self = self;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getPhonenumber() {
		return phonenumber;
	}
	
	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
