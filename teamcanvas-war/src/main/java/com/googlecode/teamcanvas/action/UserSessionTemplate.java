package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.User;
import org.apache.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public abstract class UserSessionTemplate {
    private final Logger log = Logger.getLogger(UserSessionTemplate.class);
    private User loggedInUser;

    private static final String LOGIN_USER_SESSION_KEY = "loggedInUser";

    protected void loadUserFromSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
        loggedInUser = (User) httpSession.getAttribute(LOGIN_USER_SESSION_KEY);
        log.info("Logged in loggedInUser: " + (loggedInUser != null ? loggedInUser.getEmail() : "null"));
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public boolean isUserFound(){
        return loggedInUser != null;
    }

    public boolean isLoggedInUserFound() {
        return getLoggedInUser() != null;
    }

}
