package de.hawLandshut.scrum.data;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import de.hawLandshut.scrum.model.State;
import de.hawLandshut.scrum.model.Task;


@SessionScoped
public class TaskProducer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Task task;
	
	@Produces
	@Named
	public Task getSelectedTask(){
		return task;
	}
	
	public void setSelectedTask(Task task){
		this.task = task;
	}
	
	public void prepareAddTask() {
        this.task = new Task();
        this.task.setLastChange(new Date());
        this.task.setStatus(State.ToDo);

    }

    public void prepareEditTask(Task task) {
        this.task = task;
    }

}
