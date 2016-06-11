package de.hawLandshut.scrum.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	@NamedQuery(name = Sprint.findAllAvailableSprints, query = "SELECT s FROM Sprint s WHERE ( s.backlog.owner = :owner OR s.backlog.team = :team ) AND s.status = :status")
})
@Entity
public class Sprint {
	public static final String findAllAvailableSprints = "Sprint.findAllAvailableSprint";

	@GeneratedValue
	@Id
	private Long id;
	private String name;
	@Lob
	private String description;
	private Date start;
	private Date end;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sprint")
	private List<Backlogitem> backlogitems;
	
	@ManyToOne
	private Backlog backlog;
	private Status status;
	
	
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
	public Date getStart() {
		return start;
	}
	public String getConvertStartDate(){
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.format(start);
	}
	public String getConvertEndDate(){
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.format(end);
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public List<Backlogitem> getBacklogitems() {
		return backlogitems;
	}
	public void setBacklogitems(List<Backlogitem> backlogitems) {
		this.backlogitems = backlogitems;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Backlog getBacklog() {
		return backlog;
	}
	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}
	public Status[] getStatusValue(){
		return Status.values();
	}
	public boolean getDisable(){
		if( status == Status.Plan || status == null )
			return false;
		else
			return true;
	}

	
	
}
