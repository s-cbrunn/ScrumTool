package de.hawLandshut.scrum.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Sprint;
import de.hawLandshut.scrum.model.Status;

@SessionScoped
public class SprintProducer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Sprint sprint;
	
	@Produces
	@Named
	public Sprint getSelectedSprint(){
		return sprint;
	}
	
	public void setSelectedSprint(Sprint sprint){
		this.sprint = sprint;
	}
	
	public void prepareAddSprint() {
        this.sprint = new Sprint();
        this.sprint.setStatus(Status.Plan);

    }

    public void prepareEditSprint(Sprint sprint) {
        this.sprint = sprint;
    }

}
