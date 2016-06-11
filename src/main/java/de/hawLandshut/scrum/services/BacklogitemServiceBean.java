package de.hawLandshut.scrum.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.hawLandshut.scrum.model.Backlogitem;

@Stateless
public class BacklogitemServiceBean implements BacklogitemService {

	@Inject
	EntityManager entityManager;
	
	@Override
	public List<Backlogitem> getAllBacklogitems() {
		TypedQuery<Backlogitem> query = entityManager.createNamedQuery(Backlogitem.findAllAvailableBacklogitems, Backlogitem.class);
		return query.getResultList();
	}

	@Override
	public void addBacklogitem(Backlogitem backlogitem) {
		entityManager.persist(backlogitem);
	}

	@Override
	public void deleteBacklogitem(Backlogitem backlogitem) {
		entityManager.remove(entityManager.find(Backlogitem.class, backlogitem.getId()));
	}

	@Override
	public void updateBacklogitem(Backlogitem backlogitem) {
		entityManager.merge(backlogitem);
	}

}
