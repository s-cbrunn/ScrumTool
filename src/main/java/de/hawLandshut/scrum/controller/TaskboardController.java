package de.hawLandshut.scrum.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.TabChangeEvent;

import de.hawLandshut.scrum.data.BacklogitemProducer;
import de.hawLandshut.scrum.data.TaskProducer;
import de.hawLandshut.scrum.model.Backlogitem;
import de.hawLandshut.scrum.model.Sprint;
import de.hawLandshut.scrum.model.State;
import de.hawLandshut.scrum.model.Status;
import de.hawLandshut.scrum.model.Task;
import de.hawLandshut.scrum.util.Events.AddedTask;
import de.hawLandshut.scrum.util.Events.DeletedTask;
import de.hawLandshut.scrum.util.Events.UpdatedBacklogitem;
import de.hawLandshut.scrum.util.Events.UpdatedSprint;
import de.hawLandshut.scrum.util.Events.UpdatedTask;

@Named
@ViewScoped
public class TaskboardController extends Controller implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String notify = "/taskNotify";
	
	@Inject
	@AddedTask
	private Event<Task> taskAddEvent;
	
	@Inject
	@UpdatedTask
	private Event<Task> taskUpdateEvent;
	
	@Inject
	@DeletedTask
	private Event<Task> taskDeleteEvent;
	
	@Inject
	@UpdatedSprint
	private Event<Sprint> sprintUpdateEvent;
	
	@Inject
	@UpdatedBacklogitem
	private Event<Backlogitem> backlogitemUpdateEvent;
	
	@Inject
	private BacklogitemProducer backlogitemProducer;
	
	@Inject
	private TaskProducer taskProducer;
	
	private Task selectedTask;
	
	public void onTabChange(TabChangeEvent event) {
		setActiveIndex(((TabView)event.getSource()).getIndex());
    }
	
	public void doAddTask(Backlogitem backlogitem){
		taskProducer.prepareAddTask();
		taskProducer.getSelectedTask().setBacklogitem(backlogitem);
		openDialog(Pages.DIALOG_ADD_TASK);
	}
	
	public void doAddSaveTask(){
		taskAddEvent.fire(taskProducer.getSelectedTask());
		closeDialog(null);
		pushToAllClients(notify, "Add Task", taskProducer.getSelectedTask().getName() + " was created");
	}
	
	public void doShowBacklogitem(Backlogitem backlogitem){
		backlogitemProducer.setSelectedBacklogitem(backlogitem);
		openDialog(Pages.DIALOG_SHOW_BACKLOGITEM);
	}
	
	public void doShowTask(Task task){
		taskProducer.setSelectedTask(task);
		openDialog(Pages.DIALOG_SHOW_TASK);
	}
	
	public void doDeleteTask(Task task){
		taskDeleteEvent.fire(task);
		pushToAllClients(notify,"Delete Task", task.getName() + " was deleted");
	}
	
	public void doSelectedTask(Task task){
		this.selectedTask = task;
	}
	
	private Task getSelectedTask(){
		return this.selectedTask;
	}
	
	public void doDropToDo(DragDropEvent ddEvent){
		getSelectedTask().setStatus(State.ToDo);
		getSelectedTask().setLastChange(new Date());
		taskUpdateEvent.fire(getSelectedTask());
		pushToAllClients(notify,"To Do - Task", getSelectedTask().getName() + " was moved");
	
	}
	
	public void doDropInProgress(DragDropEvent ddEvent){
		getSelectedTask().setStatus(State.InProgress);
		getSelectedTask().setLastChange(new Date());
		taskUpdateEvent.fire(getSelectedTask());
		pushToAllClients(notify,"In Progress - Task", getSelectedTask().getName() + " was moved");
		
	}
	
	public void doDropDone(DragDropEvent ddEvent){
		getSelectedTask().setStatus(State.Done);
		getSelectedTask().setLastChange(new Date());
		taskUpdateEvent.fire(getSelectedTask());
		pushToAllClients(notify,"Done - Task", getSelectedTask().getName() + " was moved");

	}
	
	public void doCloseSprint(Sprint sprint){
		sprint.setStatus(Status.Done);

		for(Backlogitem b: sprint.getBacklogitems()){
			
			if(b.getTasks().size() == 0){
				b.setBacklog(sprint.getBacklog());
				b.setSprint(null);
				backlogitemUpdateEvent.fire(b);
			}
			
			for(Task t: b.getTasks()){
				if(t.getStatus() != State.Done){
					b.setBacklog(sprint.getBacklog());
					b.setSprint(null);
					backlogitemUpdateEvent.fire(b);
				}
			}
		}	
		sprintUpdateEvent.fire(sprint);
	}


}
