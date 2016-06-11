package de.hawLandshut.scrum.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.TabChangeEvent;

import de.hawLandshut.scrum.data.BacklogListProducer;
import de.hawLandshut.scrum.data.BacklogProducer;
import de.hawLandshut.scrum.data.BacklogitemProducer;
import de.hawLandshut.scrum.data.SprintProducer;
import de.hawLandshut.scrum.model.Backlog;
import de.hawLandshut.scrum.model.Backlogitem;
import de.hawLandshut.scrum.model.Sprint;
import de.hawLandshut.scrum.model.Status;
import de.hawLandshut.scrum.util.Events.AddedSprint;
import de.hawLandshut.scrum.util.Events.DeletedSprint;
import de.hawLandshut.scrum.util.Events.UpdatedBacklogitem;
import de.hawLandshut.scrum.util.Events.UpdatedSprint;

@Named
@ViewScoped
public class SprintController extends Controller implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String notify = "/sprintNotify";
	
	@Inject
	@AddedSprint
	private Event<Sprint> sprintAddEvent;
	
	@Inject
	@UpdatedSprint
	private Event<Sprint> sprintUpdateEvent;
	
	@Inject
	@DeletedSprint
	private Event<Sprint> sprintDeleteEvent;
	
	@Inject
	@UpdatedBacklogitem
	private Event<Backlogitem> backlogitemUpdateEvent;
	
	@Inject
	private BacklogProducer backlogProducer;
	@Inject
	private BacklogitemProducer backlogitemProducer;
	@Inject
	private SprintProducer sprintProducer;
	@Inject 
	private BacklogListProducer backlogListProducer;
	
	private Backlogitem selectedBacklogitem;
	private int accordionIndex;
	
	public void doAddSprint(Backlog backlog){
		sprintProducer.prepareAddSprint();
        sprintProducer.getSelectedSprint().setBacklog(backlog);
		openDialog(Pages.DIALOG_ADD_SPRINT);
	}
	
	public String doAddSprintMobile(){
		sprintProducer.prepareAddSprint();
        sprintProducer.getSelectedSprint().setBacklog(backlogProducer.getSelectedBacklog());
		return Pages.ADD_SPRINT;
	}
	
	public void doAddSprintSave(){
		sprintAddEvent.fire(sprintProducer.getSelectedSprint());
		closeDialog(null);
		pushToAllClients(notify, "Create Sprint", sprintProducer.getSelectedSprint().getName() + " was created");
	}
	
	public String doAddSprintSaveMobile(){
		sprintAddEvent.fire(sprintProducer.getSelectedSprint());
		pushToAllClients(notify, "Create Sprint", sprintProducer.getSelectedSprint().getName() + " was created");
		return Pages.SHOW_SPRINT;
	}
	
	public void doDeleteSprint(Sprint sprint){
		String name = sprint.getName();
		List<Backlogitem> items = sprint.getBacklogitems();
		for(Backlogitem b:items){
			b.setSprint(null);
			b.setBacklog(sprint.getBacklog());
			backlogitemUpdateEvent.fire(b);
		}
		sprint.setBacklog(null);
		sprintDeleteEvent.fire(sprint);
		pushToAllClients(notify, "Delete Sprint", name + " was deleted");
	}
	
	public void doShowSprint(Sprint sprint){
		sprintProducer.setSelectedSprint(sprint);
		openDialog(Pages.DIALOG_SHOW_SPRINT);
	}
	
	public void doStartSprint(Sprint sprint){
		sprint.setStatus(Status.In_Progress);
		sprintUpdateEvent.fire(sprint);
		pushToAllClients(notify, "Start Sprint", sprint.getName() + " was started");
	}
	
	public void onTabChange(TabChangeEvent event) {
		setActiveIndex(((TabView)event.getSource()).getIndex());
		setAccordionIndex(0);
    }
	public void onAccordionChange(TabChangeEvent event) {
		setAccordionIndex(((((AccordionPanel)event.getSource()).getIndex())));
    }
	
	public void doDropBacklogitem(DragDropEvent ddEvent){
		Sprint destinationSprint = (Sprint) ddEvent.getComponent().getAttributes().get("sprint");
		getSelectedBacklogitem().setSprint(destinationSprint);
		getSelectedBacklogitem().setBacklog(null);
		backlogitemUpdateEvent.fire(getSelectedBacklogitem());
		pushToAllClients(notify, "Backlogitem -> Sprint", destinationSprint.getName() + " with new Item");
	}
	
	public String doAssignBacklogitem(Sprint sprint){
		backlogitemProducer.getSelectedBacklogitem().setSprint(sprint);
		backlogitemProducer.getSelectedBacklogitem().setBacklog(null);
		backlogitemUpdateEvent.fire(backlogitemProducer.getSelectedBacklogitem());
		pushToAllClients(notify, "Backlogitem -> Sprint", sprint.getName() + " with new Item");
		return Pages.SHOW_SPRINT;
	}

	public Backlogitem getSelectedBacklogitem() {
		return selectedBacklogitem;
	}

	public void doSelectBacklogitem(Backlogitem selectedBacklogitem) {
		this.selectedBacklogitem = selectedBacklogitem;
	}
	
	public String doSelectBacklogitemMobile(Backlogitem backlogitem) {
		backlogitemProducer.setSelectedBacklogitem(backlogitem);
		return Pages.SHOW_BACKLOGITEM_MOBIlE;
	}
	
	public String doSelectBacklogitemAssign(Backlogitem backlogitem) {
		backlogitemProducer.setSelectedBacklogitem(backlogitem);
		return Pages.ASSIGN_BACKLOGITEM;
	}
	
	public void doShowBacklogitem(Backlogitem backlogitem){
		backlogitemProducer.setSelectedBacklogitem(backlogitem);
		openDialog(Pages.DIALOG_SHOW_BACKLOGITEM);
	}
	
	public void doRemoveBacklogitemSprint(Backlogitem backlogitem){
		backlogitem.setBacklog(backlogitem.getSprint().getBacklog());
		backlogitem.setSprint(null);
		backlogitemUpdateEvent.fire(backlogitem);
		pushToAllClients(notify, "Sprint-Item -> Backlog", backlogitem.getThema() + " back");
	}
	public int getAccordionIndex() {
		return accordionIndex;
	}

	public void setAccordionIndex(int accordionIndex) {
		this.accordionIndex = accordionIndex;
	}
	
	public String doSelectedBacklog(Backlog backlog){
		backlogProducer.setSelectedBacklog(backlog);
		return Pages.SHOW_SPRINT;
	}
	
	public Backlog getSelectedBacklog(){
		List<Backlog> backlogs = backlogListProducer.getBacklogs();
		for(Backlog b:backlogs){
			if(b.getId().equals(backlogProducer.getSelectedBacklog().getId())){
				return b;
			}
		}
		return null;
	}
	
	

	
	

	
	
	

}
