package de.hawLandshut.scrum.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Backlogitem;
import de.hawLandshut.scrum.services.BacklogitemService;
import de.hawLandshut.scrum.util.Events.AddedBacklogitem;
import de.hawLandshut.scrum.util.Events.DeletedBacklogitem;
import de.hawLandshut.scrum.util.Events.UpdatedBacklogitem;

@RequestScoped
public class BacklogitemListProducer {

	private List<Backlogitem> backlogitems;
	
	@Inject
	private BacklogitemService backlogitemService;
	
	@PostConstruct
	public void init(){
		backlogitems = backlogitemService.getAllBacklogitems();
	}
	
	@Produces
	@Named
	public List<Backlogitem> getBacklogitems(){
		return backlogitems;
	}
	
	public void onBacklogitemAdded(@Observes @AddedBacklogitem Backlogitem backlogitem){
		backlogitemService.addBacklogitem(backlogitem);
		init();
	}
	
	public void onBacklogitemDeleted(@Observes @DeletedBacklogitem Backlogitem backlogitem){
		backlogitemService.deleteBacklogitem(backlogitem);
		init();
	}
		
	public void onBacklogitemUpdated(@Observes @UpdatedBacklogitem Backlogitem backlogitem){
		backlogitemService.updateBacklogitem(backlogitem);
		init();
	}
}
