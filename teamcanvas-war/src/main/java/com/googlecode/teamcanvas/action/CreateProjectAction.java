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
public class CreateProjectAction extends UserLoginTemplate{
    private final Logger log = Logger.getLogger(CreateProjectAction.class);

    private Project project;

    @EJB
    private ProjectService projectService;


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

    public String createProject() {
        String outcome = "create-new-project";
        if (isLoggedInUserFound()) {
            configureProject();
            if(saveProject()){
                outcome = "project.xhtml?faces-redirect=true";
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
        project.setProjectCreator(getUser());
    }

    public boolean isLoggedInUserFound() {
        return getUser() != null;
    }
}
