package de.hawLandshut.scrum.util;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

@Dependent
public class Resources {
	
	
	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	public Logger produceLog(InjectionPoint injectionPoing){
		return Logger.getLogger(injectionPoing.getMember().getDeclaringClass().getName(), "messages");
	}
	
	@Produces
	@RequestScoped
	public FacesContext produceFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	@Produces
	@RequestScoped
	HttpServletRequest produceRequest(){
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	@Produces
	@RequestScoped
	public RequestContext produceRequestContext(){
		return RequestContext.getCurrentInstance();
	}

}
