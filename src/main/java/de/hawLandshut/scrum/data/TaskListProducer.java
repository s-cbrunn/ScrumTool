package de.hawLandshut.scrum.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Task;
import de.hawLandshut.scrum.services.TaskService;
import de.hawLandshut.scrum.util.Events.AddedTask;
import de.hawLandshut.scrum.util.Events.DeletedTask;
import de.hawLandshut.scrum.util.Events.UpdatedTask;

@RequestScoped
public class TaskListProducer {
	
	private List<Task> tasks;
	
	@Inject
	private TaskService taskService;
	
	@PostConstruct
	public void init(){
		tasks = taskService.getAllTasks();
	}
	
	@Produces
	@Named
	public List<Task> getTasks(){
		return tasks;
	}
	
	public void onTaskAdded(@Observes @AddedTask Task task){
		taskService.addTask(task);
		init();
	}
	
	public void onTaskDeleted(@Observes @DeletedTask Task task){
		taskService.deleteTask(task);
		init();
	}
		
	public void onTaskUpdated(@Observes @UpdatedTask Task task){
		taskService.updateTask(task);
		init();
	}

}
