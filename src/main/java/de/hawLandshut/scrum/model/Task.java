package de.hawLandshut.scrum.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({ 
	@NamedQuery(name = Task.findAllAvailableTasks, query = "SELECT t FROM Task t"),
})

@Entity
public class Task {
	public static final String findAllAvailableTasks = "Task.findAllAvailableTasks";
	
	@GeneratedValue
	@Id
	private Long id;
	private String name;
	@Lob
	private String description;
	private State status;
	@ManyToOne
	private Backlogitem backlogitem;
	private Date lastChange; 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public State getStatus() {
		return status;
	}
	public void setStatus(State status) {
		this.status = status;
	}
	public Backlogitem getBacklogitem() {
		return backlogitem;
	}
	public void setBacklogitem(Backlogitem backlogitem) {
		this.backlogitem = backlogitem;
	}
	public Date getLastChange() {
		return lastChange;
	}
	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
	}
	
	
	
}
