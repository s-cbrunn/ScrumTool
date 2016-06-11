package de.hawLandshut.scrum.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hawLandshut.scrum.model.Task;
import de.hawLandshut.scrum.services.TaskService;

@Path("/master/task")
public class TaskResource {

	@Inject
	private TaskService taskService;
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getAllTasks(){
		List<Task> tasks = taskService.getAllTasks();
		return tasks;
	}

}
