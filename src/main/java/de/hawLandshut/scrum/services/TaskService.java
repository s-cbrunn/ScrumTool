package de.hawLandshut.scrum.services;

import java.util.List;

import de.hawLandshut.scrum.model.Task;

public interface TaskService {
	
	List<Task> getAllTasks();
	void addTask(Task task);
	void deleteTask(Task task);
	void updateTask(Task task);

}
