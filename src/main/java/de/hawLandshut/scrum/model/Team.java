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

@NamedQueries({
	@NamedQuery(name = Team.findAll, query = "SELECT t FROM Team t")
})

@Entity
public class Team {
	public static final String findAll = "Team.findAll";
	
	@GeneratedValue
	@Id
	private Long id;
	private String name;
	@Lob
	private String description;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
	private List<Member> members;
	
	
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
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public void setMember(Member member){
		this.members.add(member);
	}
	public String toString(){
		return getName();
	}
	
}
