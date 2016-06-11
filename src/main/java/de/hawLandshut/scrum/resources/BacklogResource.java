package de.hawLandshut.scrum.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hawLandshut.scrum.services.BacklogService;
import de.hawLandshut.scrum.model.Backlog;;

@Path("/master/backlog")
public class BacklogResource {
	
	@Inject
	private BacklogService backlogService;
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Backlog> allBacklogs(){
		List<Backlog> allBacklogs = backlogService.getAllBacklogs();
		allBacklogs.forEach(backlog -> backlog.setSprints(null));
		return allBacklogs;
	}

}
