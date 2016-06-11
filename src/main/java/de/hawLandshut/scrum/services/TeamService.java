package de.hawLandshut.scrum.services;

import java.util.List;
import de.hawLandshut.scrum.model.Team;


public interface TeamService {
		
		List<Team> getAllTeams();
		void addTeam(Team team);
		void deleteTeam(Team team);
		void updateTeam(Team team);

		
}
