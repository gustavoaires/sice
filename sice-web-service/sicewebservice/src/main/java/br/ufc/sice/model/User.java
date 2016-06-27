package br.ufc.sice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	public enum Levels{PARTICIPANT, ADMIN};
	
	@Id
	@GeneratedValue
	long id;
	
	@Column(name = "email")
	String email;
		
	@Column(name = "password")
	String password;
	
	@Column(name = "level")
	Levels level;
	
	@Column(name = "name")
	String name;

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public Levels getLevel() {
		return level;
	}
	public void setLevel(Levels level) {
		this.level = level;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			if(obj instanceof User){
				User u = (User) obj;
				return (u.getEmail().equals(email) && u.getPassword().equals(password)) || u.getId() == this.getId();
			}
			return super.equals(obj);
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", level=" + level + "]";
	}
	
}
