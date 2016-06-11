package de.hawLandshut.scrum.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@NamedQueries({ 
	@NamedQuery(name = Backlog.findAllAvailableBacklogs, query = "SELECT b FROM Backlog b"),
	@NamedQuery(name = Backlog.findByMember, query = "SELECT b FROM Backlog b WHERE b.owner = :owner OR b.team = :team" ),
})
@Entity
public class Backlog {
	public static final String findAllAvailableBacklogs = "Backlog.findAllAvailableBacklogs";
	public static final String findByMember = "Backlog.findByMember";
	
	@GeneratedValue
	@Id
	private Long id;
	private String name;
	@Lob
	private String description;
	@OneToOne
	private Member owner;
	@OneToOne
	private Team team;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "backlog")
	private List<Backlogitem> items;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "backlog")
	private List<Sprint> sprints;
	
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
	public Member getOwner() {
		return owner;
	}
	public void setOwner(Member owner) {
		this.owner = owner;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public List<Backlogitem> getItems() {
		return items;
	}
	public List<Sprint> getSprints() {
		return sprints;
	}
	public void setSprints(List<Sprint> sprints) {
		this.sprints = sprints;
	}
	public void setItems(List<Backlogitem> items) {
		this.items = items;
	}
	
	
}
