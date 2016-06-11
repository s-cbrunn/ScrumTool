package de.hawLandshut.scrum.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.hawLandshut.scrum.model.Task;

@Stateless
public class TaskServiceBean implements TaskService {
	
	@Inject
	EntityManager entityManager;
	
	@Override
	public List<Task> getAllTasks() {
		TypedQuery<Task> query = entityManager.createNamedQuery(Task.findAllAvailableTasks, Task.class);
		return query.getResultList();
	}

	@Override
	public void addTask(Task task) {
		entityManager.persist(task);

	}

	@Override
	public void deleteTask(Task task) {
		entityManager.remove(entityManager.find(Task.class, task.getId()));

	}

	@Override
	public void updateTask(Task task) {
		entityManager.merge(task);

	}

}
