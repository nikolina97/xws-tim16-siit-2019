package com.xws.application.dto;

import java.util.List;

import com.xws.application.model.TRole;

public class UserRegistrationDTO {
	private String firstName;
	private String lastName;
	private String universityName;
	private String universityCity;
	private String universityCountry;
	private List<String> expertises;
	private String email;
	private String password;
	private TRole role;
	public UserRegistrationDTO() {
		super();
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public String getUniversityCity() {
		return universityCity;
	}
	public void setUniversityCity(String universityCity) {
		this.universityCity = universityCity;
	}
	public String getUniversityCountry() {
		return universityCountry;
	}
	public void setUniversityCountry(String universityCountry) {
		this.universityCountry = universityCountry;
	}
	public List<String> getExpertises() {
		return expertises;
	}
	public void setExpertises(List<String> expertises) {
		this.expertises = expertises;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public TRole getRole() {
		return role;
	}
	public void setRole(TRole role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UserRegistrationDTO [firstName=" + firstName + ", lastName=" + lastName + ", universityName="
				+ universityName + ", universityCity=" + universityCity + ", universityCountry=" + universityCountry
				+ ", expertises=" + expertises + ", email=" + email + ", password="
				+ password + ", role=" + role + "]";
	}
}
