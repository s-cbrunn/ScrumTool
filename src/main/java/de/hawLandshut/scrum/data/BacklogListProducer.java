package de.hawLandshut.scrum.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Backlog;
import de.hawLandshut.scrum.services.BacklogService;
import de.hawLandshut.scrum.util.Events.AddedBacklog;
import de.hawLandshut.scrum.util.Events.DeletedBacklog;
import de.hawLandshut.scrum.util.Events.UpdatedBacklog;



@RequestScoped
public class BacklogListProducer {
	
	private List<Backlog> backlogs;
   
	@Inject
	private BacklogService backlogService;
	
	@PostConstruct
	public void init(){
		backlogs = backlogService.getAllBacklogs();
	}
	
	@Produces
	@Named
	public List<Backlog> getBacklogs(){
		return backlogs;
	}
	
	public void onBacklogAdded(@Observes @AddedBacklog Backlog backlog){
		backlogService.addBacklog(backlog);
		init();
	}
	
	public void onBacklogDeleted(@Observes @DeletedBacklog Backlog backlog){
		backlogService.deleteBacklog(backlog);
		init();
	}
		
	public void onBacklogUpdated(@Observes @UpdatedBacklog Backlog backlog){
		backlogService.updateBacklog(backlog);
		init();
	}
	
}
