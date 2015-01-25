package com.noblebanking.bean;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserBean {

	//@NotNull
	
	@Size(min = 2, max = 9, message = "Your firstName must between 2 and 9 characters")
	@NotEmpty(message = "Please enter your firstName")
	@Pattern(regexp="^[a-zA-Z]+$", message="firstName can only characters")
	private String firstname;
	@Size(min = 2, max = 9, message = "Your lastName must between 2 and 9 characters")
	@NotEmpty(message = "Please enter your lastName")
	@Pattern(regexp="^[a-zA-Z]+$", message="lastName can only characters")
	private String lastname;
	@NotEmpty(message = "Please enter your email")
	@Size(min = 3, max = 28, message = "Your email must between 3 and 28 characters")
    @Email(message = "Please enter valid email(length < 28 characters) Ex: abc@xyz.com")
	private String email;
	@NotEmpty(message = "Please enter your mobile")
	@Pattern(regexp="^[0-9]{10}$", message="Enter valid 10 digit number")
	private String mobile;
	@NotEmpty(message = "Please enter your address")
	@Pattern(regexp="^[a-zA-Z ]+$", message="Enter valid address only characters")
	@Size(min = 5, max = 15, message = "Your address must between 5 and 15 characters ")
	private String address;
/*	@NotEmpty(message = "Please enter your secquestion")
	@Size(min = 5, max = 15, message = "Your secquestion must between 5 and 15 characters ")
	private String secquestion;
	@NotEmpty(message = "Please enter your secquesanswer")
	@Size(min = 5, max = 15, message = "Your secquesanswer must between 5 and 15 characters ")
	private String secquesanswer;
*/	private String username;
	private String password;
	private String role;
	private int empid;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

/*	public String getSecquestion() {
		return secquestion;
	}

	public void setSecquestion(String secquestion) {
		this.secquestion = secquestion;
	}

	public String getSecquesanswer() {
		return secquesanswer;
	}

	public void setSecquesanswer(String secquesanswer) {
		this.secquesanswer = secquesanswer;
	}*/

}