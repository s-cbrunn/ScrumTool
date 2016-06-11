package de.hawLandshut.scrum.services;

import java.util.List;

import de.hawLandshut.scrum.model.Sprint;

public interface SprintService {
	
	List<Sprint> getAllSprints();
	void addSprint(Sprint sprint);
	void deleteSprint(Sprint sprint);
	void updateSprint(Sprint sprint);

}
