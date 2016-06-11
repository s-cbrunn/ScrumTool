package de.hawLandshut.scrum.controller;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;

import de.hawLandshut.scrum.data.BacklogProducer;
import de.hawLandshut.scrum.data.BacklogitemProducer;
import de.hawLandshut.scrum.model.Backlog;
import de.hawLandshut.scrum.model.Backlogitem;
import de.hawLandshut.scrum.model.Sprint;
import de.hawLandshut.scrum.model.Task;
import de.hawLandshut.scrum.util.Events.AddedBacklog;
import de.hawLandshut.scrum.util.Events.AddedBacklogitem;
import de.hawLandshut.scrum.util.Events.DeletedBacklog;
import de.hawLandshut.scrum.util.Events.DeletedBacklogitem;
import de.hawLandshut.scrum.util.Events.DeletedSprint;
import de.hawLandshut.scrum.util.Events.DeletedTask;
import de.hawLandshut.scrum.util.Events.UpdatedBacklog;
import de.hawLandshut.scrum.util.Events.UpdatedBacklogitem;

@Named
@ViewScoped
public class BacklogController extends Controller implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String notify = "/backlogNotify";

	@Inject
	@AddedBacklog
	private Event<Backlog> backlogAddEvent;
	
	@Inject
	@DeletedBacklog
	private Event<Backlog> backlogDeleteEvent;
	
	@Inject
	@AddedBacklogitem
	private Event<Backlogitem> backlogitemAddEvent;
	
	@Inject
	@DeletedBacklogitem
	private Event<Backlogitem> backlogitemDeletedEvent;
	
	@Inject
	@UpdatedBacklogitem
	private Event<Backlogitem> backlogitemUpdateEvent;

	@Inject
	@UpdatedBacklog
	private Event<Backlog> backlogUpdateEvent;
	
	@Inject
	@DeletedSprint
	private Event<Sprint> sprintDeleteEvent;
	
	@Inject
	@DeletedTask
	private Event<Task> taskDeleteEvent;

	@Inject
	private BacklogProducer backlogProducer;
	@Inject 
	private BacklogitemProducer backlogitemProducer;
	
	

	public void doAddBacklog() {
		backlogProducer.prepareAddBacklog();
		openDialog(Pages.DIALOG_ADD_BACKLOG);
	}
	
	public void doAssignTeam(Backlog backlog){
		backlogProducer.setSelectedBacklog(backlog);
		openDialog(Pages.DIALOG_ASSIGN_TEAM);
	}
	
	public void doAssignTeamSave(){
		backlogUpdateEvent.fire(backlogProducer.getSelectedBacklog());
		closeDialog(null);
		pushToAllClients(notify, "Assign Team", backlogProducer.getSelectedBacklog().getTeam().getName() + " was assigned");
	}

	public void doAddBacklogSave() {
		backlogAddEvent.fire(backlogProducer.getSelectedBacklog());
		closeDialog(null);
		pushToAllClients(notify, "Add Backlog", backlogProducer.getSelectedBacklog().getName() + " was created");
	}

	public void doAddBacklogitem(Backlog backlog) {
		backlogitemProducer.prepareAddBacklogitem();
		backlogitemProducer.getSelectedBacklogitem().setBacklog(backlog);
		openDialog(Pages.DIALOG_ADD_BACKLOGITEM);
	}

	public void doAddBacklogitemSave() {
		backlogitemAddEvent.fire(backlogitemProducer.getSelectedBacklogitem());
		closeDialog(null);
		pushToAllClients(notify, "Add Backlogitem", backlogitemProducer.getSelectedBacklogitem().getThema() + " was created");
	}
	
	public void doUpdateBacklogitemSave(){
		backlogitemUpdateEvent.fire(backlogitemProducer.getSelectedBacklogitem());
		closeDialog(null);
		pushToAllClients(notify, "Update Backlogitem", backlogitemProducer.getSelectedBacklogitem().getThema() + " was updated");
	}

	public void doDeleteBacklog(Backlog backlog) {
		String name = backlog.getName();
		
		for(Backlogitem item: backlog.getItems()){
			for(Task task:item.getTasks()){
				taskDeleteEvent.fire(task);
			}
			backlogitemDeletedEvent.fire(item);
		}
		
		for(Sprint sprint: backlog.getSprints()){
			for(Backlogitem item:sprint.getBacklogitems()){
				for(Task task:item.getTasks()){
					taskDeleteEvent.fire(task);
				}
				backlogitemDeletedEvent.fire(item);
			}
			sprintDeleteEvent.fire(sprint);
		}
		
		
		backlogDeleteEvent.fire(backlog);
		pushToAllClients(notify, "Delete Backlog", name + " was deleted");
	}
	
	public void doShowBacklog(Backlog backlog){
		backlogProducer.setSelectedBacklog(backlog);
		openDialog(Pages.DIALOG_SHOW_BACKLOG);
	}

	public void doDeleteBacklogitem(Backlogitem backlogitem) {
		String name = backlogitem.getThema();
		backlogitemDeletedEvent.fire(backlogitem);
		pushToAllClients(notify, "Delete Backlogitem", name + " was deleted");
	}
	
	public void doEditBacklogitem(Backlogitem backlogitem){
		backlogitemProducer.setSelectedBacklogitem(backlogitem);
		openDialog(Pages.DIALOG_EDIT_BACKLOGITEM);
	}
	
	public void onTabChange(TabChangeEvent event) {
		int index = ((AccordionPanel)event.getSource()).getIndex();
        if(index == 0)
        	setActiveIndex(-1);
        else
        	setActiveIndex(index);
    }

	
}
