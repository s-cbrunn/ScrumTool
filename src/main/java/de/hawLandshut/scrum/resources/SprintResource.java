package de.hawLandshut.scrum.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hawLandshut.scrum.model.Sprint;
import de.hawLandshut.scrum.services.SprintService;

@Path("/master/sprint")
public class SprintResource {

	@Inject
	private SprintService sprintService;
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sprint> getAllSprints(){
		List<Sprint> sprints = sprintService.getAllSprints();
		sprints.forEach(sprint -> sprint.setBacklogitems(null));
		return sprints;
	}

}
