package de.hawLandshut.scrum.services;

import java.util.List;

import de.hawLandshut.scrum.model.Backlog;


public interface BacklogService {
	
	List<Backlog> getAllBacklogs();
	void addBacklog(Backlog backlog);
	void deleteBacklog(Backlog backlog);
	void updateBacklog(Backlog backlog);

}
