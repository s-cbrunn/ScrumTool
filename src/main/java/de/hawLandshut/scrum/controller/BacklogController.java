package de.hawLandshut.scrum.controller;

import java.io.Serializable;
import java.util.List;

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
import de.hawLandshut.scrum.model.Team;
import de.hawLandshut.scrum.services.BacklogService;
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
	@Inject
	private BacklogService backlogService;
	
	

	public void doAddBacklog() {
		backlogProducer.prepareAddBacklog();
		openDialog(Pages.DIALOG_ADD_BACKLOG);
	}
	
	public String doAddBacklogMobile() {
		backlogProducer.prepareAddBacklog();
		return Pages.ADD_BACKLOG_MOBILE;
	}
	
	public void doAssignTeam(Backlog backlog){
		backlogProducer.setSelectedBacklog(backlog);
		openDialog(Pages.DIALOG_ASSIGN_TEAM);
	}
	
	public String doAssignTeamMobile(Team team){
		backlogProducer.getSelectedBacklog().setTeam(team);
		backlogUpdateEvent.fire(backlogProducer.getSelectedBacklog());		
		pushToAllClients(notify, "Assign Team", backlogProducer.getSelectedBacklog().getTeam().getName() + " was assigned");
		return Pages.SHOW_BACKLOG;
	}
	
	public String doNoAssignTeamMobile(){
		backlogProducer.getSelectedBacklog().setTeam(null);
		backlogUpdateEvent.fire(backlogProducer.getSelectedBacklog());
		pushToAllClients(notify, "Assign Team", "NO TEAM ASSIGNED" + " was assigned");
		return Pages.SHOW_BACKLOG;
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
	
	public String doAddBacklogSaveMobile() {
		backlogAddEvent.fire(backlogProducer.getSelectedBacklog());
		pushToAllClients(notify, "Add Backlog", backlogProducer.getSelectedBacklog().getName() + " was created");
		backlogProducer.prepareAddBacklog();		
		return Pages.MAIN;
	}

	public void doAddBacklogitem(Backlog backlog) {
		backlogitemProducer.prepareAddBacklogitem();
		backlogitemProducer.getSelectedBacklogitem().setBacklog(backlog);
		openDialog(Pages.DIALOG_ADD_BACKLOGITEM);
	}
	
	public String doAddBacklogitemMobile(){
		backlogitemProducer.prepareAddBacklogitem();
		backlogitemProducer.getSelectedBacklogitem().setBacklog(backlogProducer.getSelectedBacklog());
		return Pages.ADD_BACKLOGITEM_MOBILE;
	}

	public void doAddBacklogitemSave() {
		backlogitemAddEvent.fire(backlogitemProducer.getSelectedBacklogitem());
		closeDialog(null);
		pushToAllClients(notify, "Add Backlogitem", backlogitemProducer.getSelectedBacklogitem().getThema() + " was created");
	}
	
	public String doAddBacklogitemSaveMobile(){
		backlogitemAddEvent.fire(backlogitemProducer.getSelectedBacklogitem());
		pushToAllClients(notify, "Add Backlogitem", backlogitemProducer.getSelectedBacklogitem().getThema() + " was created");
		backlogitemProducer.prepareAddBacklogitem();
		return Pages.SHOW_BACKLOG;
	}
	
	public void doUpdateBacklogitemSave(){
		backlogitemUpdateEvent.fire(backlogitemProducer.getSelectedBacklogitem());
		closeDialog(null);
		pushToAllClients(notify, "Update Backlogitem", backlogitemProducer.getSelectedBacklogitem().getThema() + " was updated");
	}
	
	public String doUpdateBacklogitemSaveMobile(){
		backlogitemUpdateEvent.fire(backlogitemProducer.getSelectedBacklogitem());
		pushToAllClients(notify, "Update Backlogitem", backlogitemProducer.getSelectedBacklogitem().getThema() + " was updated");
		return Pages.SHOW_BACKLOG;
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
	
	public String doDeleteBacklogMobile(){
		doDeleteBacklog(backlogProducer.getSelectedBacklog());
		return Pages.MAIN;
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
	
	public String doEditBacklogitemMobile(Backlogitem backlogitem){
		backlogitemProducer.setSelectedBacklogitem(backlogitem);
		return Pages.EDIT_BACKLOGITEM_MOBILE;
	}
	
	public String doSelectedBacklog(Backlog backlog){
		backlogProducer.setSelectedBacklog(backlog);
		return Pages.SHOW_BACKLOG;
	}
	
	public void onTabChange(TabChangeEvent event) {
		int index = ((AccordionPanel)event.getSource()).getIndex();
        if(index == 0)
        	setActiveIndex(-1);
        else
        	setActiveIndex(index);
    }
	
	public List<Backlogitem> getBacklogitems(){
		List<Backlog> backlog = backlogService.getAllBacklogs();
		for(Backlog b : backlog){
			if(b.getId().equals(backlogProducer.getSelectedBacklog().getId())){
				return b.getItems();
			}
		}
		return null;
	}

	
}
