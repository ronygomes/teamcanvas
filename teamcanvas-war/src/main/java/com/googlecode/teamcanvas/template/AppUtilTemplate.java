package com.googlecode.teamcanvas.template;

import com.googlecode.teamcanvas.domain.User;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.util.Map;

public abstract class AppUtilTemplate {
    private final Logger log = Logger.getLogger(AppUtilTemplate.class);
    private User loggedInUser;
    private Map<String, String> parameterMap;
    private FacesContext facesContext;

    private static final String LOGIN_USER_SESSION_KEY = "loggedInUser";

    protected void loadUserFromSession() {
        facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
        loggedInUser = (User) httpSession.getAttribute(LOGIN_USER_SESSION_KEY);
        log.info("Logged in loggedInUser: " + (loggedInUser != null ? loggedInUser.getEmail() : "null"));
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public boolean isUserFound() {
        return loggedInUser != null;
    }

    public boolean isLoggedInUserFound() {
        return getLoggedInUser() != null;
    }

    public void loadParamMap() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        parameterMap = externalContext.getRequestParameterMap();
    }

    public boolean paramExists(String paramName) {
        String paramValue = parameterMap.get(paramName);
        return paramValue != null;
    }

    public long getLongParamValue(String paramName) {
        String paramValue = parameterMap.get(paramName);
        log.info("Parameter:(" + paramName + " ) " + paramValue);
        return convertStringToLong(paramValue);
    }

    private long convertStringToLong(String stringValue) {
        try {
            return Long.parseLong(stringValue);
        } catch (NumberFormatException n) {
            log.info("Unable to convert " + stringValue);
            return -1L;
        }
    }

    public void initUtilParams() {
        loadUserFromSession();
        loadParamMap();

    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public void addErrorMessage(String message, UIComponent component) {
        if (facesContext != null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        FacesMessage invalidUserMessage = new FacesMessage(message);
        facesContext.addMessage(component.getClientId(facesContext), invalidUserMessage);
    }

    public String getStringParamValue(String paramName) {
        String paramValue = parameterMap.get(paramName);
        log.info("Parameter:(" + paramName + ") " + paramValue);
        return paramValue;
    }


}
