package de.hawLandshut.scrum.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Sprint;

import de.hawLandshut.scrum.services.SprintService;
import de.hawLandshut.scrum.util.Events.AddedSprint;
import de.hawLandshut.scrum.util.Events.DeletedSprint;
import de.hawLandshut.scrum.util.Events.UpdatedSprint;

@RequestScoped
public class SprintListProducer {
	
	private List<Sprint> sprints;
	
	@Inject
	private SprintService sprintService;
	
	@PostConstruct
	public void init(){
		sprints = sprintService.getAllSprints();
	}
	
	@Produces
	@Named
	public List<Sprint> getSprints(){
		return sprints;
	}
	
	public void onSprintAdded(@Observes @AddedSprint Sprint sprint){
		sprintService.addSprint(sprint);
		init();
	}
	
	public void onSprintDeleted(@Observes @DeletedSprint Sprint sprint){
		sprintService.deleteSprint(sprint);
		init();
	}
		
	public void onSprintUpdated(@Observes @UpdatedSprint Sprint sprint){
		sprintService.updateSprint(sprint);
		init();
	}

}
