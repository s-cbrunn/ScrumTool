package de.hawLandshut.scrum.services;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.hawLandshut.scrum.model.Backlog;
import de.hawLandshut.scrum.model.Member;


@Stateless
public class BacklogServiceBean implements BacklogService {

	@Inject
	EntityManager entityManager;
	
	@Resource
    private SessionContext sessionContext;
	
	@Override
	public List<Backlog> getAllBacklogs() {
		TypedQuery<Backlog> query = entityManager.createNamedQuery(Backlog.findByMember, Backlog.class);
		query.setParameter("owner", getLoggedInMember());
		query.setParameter("team", getLoggedInMember().getTeam());
		return query.getResultList();
	}

	@Override
	public void addBacklog(Backlog backlog) {
		backlog.setOwner(getLoggedInMember());
		entityManager.persist(backlog);
	}

	@Override
	public void deleteBacklog(Backlog backlog) {
		entityManager.remove(entityManager.find(Backlog.class, backlog.getId()));

	}

	@Override
	public void updateBacklog(Backlog backlog) {
		entityManager.merge(backlog);
	}
	
	private Member getLoggedInMember(){
		String memberEmail = sessionContext.getCallerPrincipal().getName();
		Member member = entityManager.createNamedQuery(Member.findByEmail, Member.class).setParameter("email", memberEmail).getSingleResult();
		return member;
	}
	
}
