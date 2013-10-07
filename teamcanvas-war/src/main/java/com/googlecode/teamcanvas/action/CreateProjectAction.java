package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.User;
import com.googlecode.teamcanvas.service.ProjectService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

@Named
@RequestScoped
public class CreateProjectAction {
    private final Logger log = Logger.getLogger(CreateProjectAction.class);

    private Project project;
    private User user;

    @EJB
    private ProjectService projectService;

    private static final String LOGIN_USER_SESSION_KEY = "loggedInUser";

    @PostConstruct
    private void setUp(){
        initializeProject();
        loadUserFromSession();
    }

    private void initializeProject() {
        if(project == null){
            project = new Project();
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void loadUserFromSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
        user = (User) httpSession.getAttribute(LOGIN_USER_SESSION_KEY);
        log.info("Logged in user: " + user);
    }

    public String createProject() {
        String outcome = "create-new-project";
        if (isLoggedInUserFound()) {
            configureProject();
            if(saveProject()){
                outcome = "index.xhtml?faces-redirect=true";
            }
        }
        return outcome;
    }

    private boolean saveProject() {
        return projectService.saveProject(project);
    }

    private void configureProject() {
        setLoggedInUserAsProjectCreator();
        setCurrentTimeAsProjectCreationTime();
    }

    private void setCurrentTimeAsProjectCreationTime() {
        Date currentTime = Calendar.getInstance().getTime();
        project.setProjectCreationTime(currentTime);
    }

    private void setLoggedInUserAsProjectCreator() {
        project.setProjectCreator(user);
    }

    public boolean isLoggedInUserFound() {
        return user != null;
    }
}
