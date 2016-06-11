package de.hawLandshut.scrum.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hawLandshut.scrum.model.Backlogitem;
import de.hawLandshut.scrum.services.BacklogitemService;

@Path("/master/backlogitem")
public class BacklogitemResource {

	@Inject
	private BacklogitemService backlogitemService;
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Backlogitem> getAllBacklogitems(){
		List<Backlogitem> allBacklogitems = backlogitemService.getAllBacklogitems();
		allBacklogitems.forEach(backlogitem -> {backlogitem.setTasks(null);});
		return allBacklogitems;
	
	}

}
