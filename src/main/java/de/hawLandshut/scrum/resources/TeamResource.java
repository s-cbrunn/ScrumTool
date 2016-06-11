package de.hawLandshut.scrum.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hawLandshut.scrum.model.Team;
import de.hawLandshut.scrum.services.TeamService;

@Path("/master/team")
public class TeamResource {

	@Inject
	private TeamService teamservice;
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Team> getAllTeams(){
		List<Team> allTeams = teamservice.getAllTeams();
		allTeams.forEach(team ->{
			team.setMembers(null);;
		});
		return allTeams;
	}

}
