package br.ufc.sice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "participation")
public class Participation {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	long id;
	
	@Column(name = "id_subevent")
	long idSubEvent;
	
	@Column(name = "id_user")
	long idUser;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdSubEvent() {
		return idSubEvent;
	}

	public void setIdSubEvent(long idSubEvent) {
		this.idSubEvent = idSubEvent;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	
	

}
