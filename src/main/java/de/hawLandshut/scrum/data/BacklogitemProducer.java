package de.hawLandshut.scrum.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import de.hawLandshut.scrum.model.Backlogitem;




@SessionScoped
public class BacklogitemProducer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Backlogitem backlogitem;
	
	@Produces
	@Named
	public Backlogitem getSelectedBacklogitem(){
		return backlogitem;
	}
	
	public void setSelectedBacklogitem(Backlogitem backlogitem){
		this.backlogitem = backlogitem;
	}
	
	public void prepareAddBacklogitem() {
        this.backlogitem = new Backlogitem();
        
    }

    public void prepareEditBacklogitem(Backlogitem backlogitem) {
        this.backlogitem = backlogitem;
    }

}
