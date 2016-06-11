package de.hawLandshut.scrum.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Backlog;


@SessionScoped
public class BacklogProducer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Backlog backlog;
	
	@Produces
	@Named
	public Backlog getSelectedBacklog(){
		return backlog;
	}
	
	public void setSelectedBacklog(Backlog backlog){
		this.backlog = backlog;
	}
	
	public void prepareAddBacklog() {
        this.backlog = new Backlog();
    }

    public void prepareEditBacklog(Backlog backlog) {
        this.backlog = backlog;
    }
	

}
