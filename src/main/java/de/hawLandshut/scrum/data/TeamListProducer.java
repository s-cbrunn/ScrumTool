package de.hawLandshut.scrum.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Team;
import de.hawLandshut.scrum.services.TeamService;
import de.hawLandshut.scrum.util.Events.AddedTeam;
import de.hawLandshut.scrum.util.Events.DeletedTeam;
import de.hawLandshut.scrum.util.Events.UpdatedTeam;


@RequestScoped
public class TeamListProducer {

	private List<Team> teams;
	
	@Inject
	private TeamService teamService;
	
	@PostConstruct
	public void init(){
		teams = teamService.getAllTeams();
	}
	
	@Produces
	@Named
	public List<Team> getTeams(){
		return teams;
	}
	
	public void onTeamAdded(@Observes @AddedTeam Team team){
		teamService.addTeam(team);
		init();
	}
	
	public void onTeamDeleted(@Observes @DeletedTeam Team team){
		teamService.deleteTeam(team);
		init();
	}
	
	public void onTeamUpdated(@Observes @UpdatedTeam Team team){
		teamService.updateTeam(team);
		init();
	}
	
}
