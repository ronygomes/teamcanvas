package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.User;
import org.apache.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public abstract class UserLoginTemplate {
    private final Logger log = Logger.getLogger(UserLoginTemplate.class);
    private User user;

    private static final String LOGIN_USER_SESSION_KEY = "loggedInUser";

    protected void loadUserFromSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
        user = (User) httpSession.getAttribute(LOGIN_USER_SESSION_KEY);
        log.info("Logged in user: " + (user != null ? user.getEmail() : "null") );
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isUserFound(){
        return user != null;
    }
}
