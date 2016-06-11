package de.hawLandshut.scrum.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({ 
	@NamedQuery(name = Backlogitem.findAllAvailableBacklogitems, query = "SELECT b FROM Backlogitem b"),
})
@Entity
public class Backlogitem {
	public static final String findAllAvailableBacklogitems = "Backlogitem.findAllAvailableBacklogitmes";
	
	@GeneratedValue
	@Id
	private Long id;
	@ManyToOne
	private Backlog backlog;
	private String thema;
	@Lob
	private String story;
	@Lob
	private String accept;

	private String type;
	private String estimate;
	@ManyToOne
	private Sprint sprint;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "backlogitem")
	private List<Task> tasks;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Backlog getBacklog() {
		return backlog;
	}
	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}
	public String getThema() {
		return thema;
	}
	public void setThema(String thema) {
		this.thema = thema;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEstimate() {
		return estimate;
	}
	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}
	public Sprint getSprint() {
		return sprint;
	}
	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	public Type[] getTypes(){
		return Type.values();
	}
	public State[] getStates(){
		return State.values();
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	
	

}
