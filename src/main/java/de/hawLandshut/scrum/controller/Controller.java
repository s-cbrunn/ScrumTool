package de.hawLandshut.scrum.controller;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@Named
@RequestScoped
public class Controller {
	
	@Inject 
	protected RequestContext requestContext;
	@Inject 
	protected FacesContext facesContext;
	
	protected int activeIndex;
	

	public String logOut() {
		HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
		session.invalidate();
		return Pages.LOGIN;
	}
	
	public void doCancel(){
		closeDialog(null);
	}

	protected void openDialog(String path){
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("resizable", false);
		requestContext.openDialog(path, options, null);
	}
	
	protected void closeDialog(Object data){
		requestContext.closeDialog(data);
	}
	
	protected void pushToAllClients(String destination, String detail, String message){
		EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(destination, new FacesMessage(detail, message));
	}
	
	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
			this.activeIndex = activeIndex;
	}
	
}
