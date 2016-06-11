package de.hawLandshut.scrum.services;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.hawLandshut.scrum.model.Member;
import de.hawLandshut.scrum.model.Sprint;
import de.hawLandshut.scrum.model.Status;

@Stateless
public class SprintServiceBean implements SprintService {

	@Inject
	EntityManager entityManager;
	
	@Resource
    private SessionContext sessionContext;
	
	@Override
	public List<Sprint> getAllSprints() {
		TypedQuery<Sprint> query = entityManager.createNamedQuery(Sprint.findAllAvailableSprints, Sprint.class);
		query.setParameter("owner", getLoggedInMember());
		query.setParameter("team", getLoggedInMember().getTeam());
		query.setParameter("status", Status.In_Progress);
		return query.getResultList();
	}

	@Override
	public void addSprint(Sprint sprint) {
		entityManager.persist(sprint);

	}

	@Override
	public void deleteSprint(Sprint sprint) {
		entityManager.remove(entityManager.find(Sprint.class, sprint.getId()));

	}

	@Override
	public void updateSprint(Sprint sprint) {
		entityManager.merge(sprint);

	}
	
	private Member getLoggedInMember(){
		String memberEmail = sessionContext.getCallerPrincipal().getName();
		Member member = entityManager.createNamedQuery(Member.findByEmail, Member.class).setParameter("email", memberEmail).getSingleResult();
		return member;
	}

}
