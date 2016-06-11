package de.hawLandshut.scrum.services;

import java.util.List;

import de.hawLandshut.scrum.model.Backlogitem;

public interface BacklogitemService {

	List<Backlogitem> getAllBacklogitems();
	void addBacklogitem(Backlogitem backlogitem);
	void deleteBacklogitem(Backlogitem backlogitem);
	void updateBacklogitem(Backlogitem backlogitem);
	
}
