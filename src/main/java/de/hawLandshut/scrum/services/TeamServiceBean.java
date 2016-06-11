package de.hawLandshut.scrum.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.hawLandshut.scrum.model.Team;

@Stateless
public class TeamServiceBean implements TeamService {
	
	@Inject
	EntityManager entityManager;
	
	@Override
	public List<Team> getAllTeams() {
		TypedQuery<Team> query = entityManager.createNamedQuery(Team.findAll, Team.class);
		return query.getResultList();
	}

	@Override
	public void addTeam(Team team) {
		entityManager.persist(team);
	}

	@Override
	public void deleteTeam(Team team) {
		entityManager.remove(entityManager.find(Team.class, team.getId()));
	}

	@Override
	public void updateTeam(Team team) {
		entityManager.merge(team);
	}



}
