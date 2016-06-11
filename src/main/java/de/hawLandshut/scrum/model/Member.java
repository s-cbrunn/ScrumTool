package de.hawLandshut.scrum.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;



@NamedQueries({ 
	@NamedQuery(name = Member.findAllAvailableMembers, query = "SELECT m FROM Member m WHERE m.team IS NULL"),
	@NamedQuery(name = Member.findByEmail, query = "SELECT m FROM Member m WHERE m.email = :email")
})
@Entity
public class Member {
	public static final String findAllAvailableMembers = "Member.findAllAvailableMembers";
	public static final String findByEmail = "Member.findByEmail";

	@Id
	private String email;
	private String id;
	private String lastname;
	private String firstname;
	private String phone;
	private String password;
	private String department;
	private String role;
	@ManyToOne
	private Team team;


	public String getEmail() {
		
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		setId(getSHA(email));
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		
		this.password = getSHA(password);
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getRole() {
		
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Role[] getRoles() {
		return Role.values();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private String getSHA(String msg){
		MessageDigest md;
		try {

			md = MessageDigest.getInstance("SHA-256");

		} catch (NoSuchAlgorithmException ex) {
			System.out.println(ex.getMessage());
			return "";
		}
		md.update(msg.getBytes());
		byte[] shaDig = md.digest();
		
		return String.format("%064x", new java.math.BigInteger(1, shaDig));
	}
	
}
