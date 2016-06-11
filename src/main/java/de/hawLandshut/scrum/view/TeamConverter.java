package de.hawLandshut.scrum.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import de.hawLandshut.scrum.data.TeamListProducer;
import de.hawLandshut.scrum.model.Team;

@FacesConverter("teamConverter")
public class TeamConverter implements Converter {

	@Inject
	private TeamListProducer teamListProducer;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		if (arg2 != null && arg2.trim().length() > 0) {
			try {
				for (Team t : teamListProducer.getTeams()) {
					if (t.getId() == Long.valueOf(arg2))
						return t;
				}
				return null;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Team."));
			}
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			
			return String.valueOf(((Team) arg2).getId());
		} else {
			return null;
		}
	}

}
