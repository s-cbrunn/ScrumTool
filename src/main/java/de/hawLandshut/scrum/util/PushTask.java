package de.hawLandshut.scrum.util;

import javax.faces.application.FacesMessage;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;


@PushEndpoint("/taskNotify")
public class PushTask {
    @OnMessage(encoders = {JSONEncoder.class})
    public FacesMessage onMessage(FacesMessage message) {
        return message;
    }
}
