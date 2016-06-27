package br.ufc.sice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "subevent")
public class SubEvent implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
    @Column(name = "id")
	private long id;
	
	@Column(name = "id_event")
    private long id_event;
	
	@Column(name = "title")
    private String title;
	
	@Column(name = "subtitle")
    private String subtitle;
	
	@Column(name = "description")
    private String description;
	
	@Column(name = "begin")
	@Temporal(TemporalType.TIMESTAMP)
    private Date begin;
    
	@Column(name = "end")
	@Temporal(TemporalType.TIMESTAMP)
    private Date end;	
    
	public long getId_event() {
		return id_event;
	}

	public void setId_event(long id_event) {
		this.id_event = id_event;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "SubEvent [id=" + id + ", id_event=" + id_event + ", title=" + title + ", subtitle=" + subtitle
				+ ", description=" + description + ", begin=" + begin + ", end=" + end + "]";
	}
	
	public double getDuration(){
		return 0;
	}
	
}
