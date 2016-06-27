package br.ufc.sice.model;

public class UserSummary {

	long id;
	String name, email;
	double hours;
	
	public UserSummary(long id, String name, String email, double hours){
		this.id = id;
		this.name = name;
		this.email = email;
		this.hours = hours;
	}
	
	public void addHours(double hours){
		this.hours += hours;
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if(obj instanceof User){
			return ((User) obj).getId() == this.id;
		}
		
		return super.equals(obj);
	}
}
