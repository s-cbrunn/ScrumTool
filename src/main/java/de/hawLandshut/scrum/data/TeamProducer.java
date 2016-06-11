package de.hawLandshut.scrum.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Team;

@SessionScoped
public class TeamProducer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Team team;
	
	
	@Produces
	@Named
	public Team getSelectedTeam(){
		return team;
	}
	
	public void setSelectedTeam(Team team){
		this.team = team;
	}
	
	public void prepareAddTeam() {
        this.team = new Team();

    }

    public void prepareEditTeam(Team team) {
        this.team = team;
    }
	

}
