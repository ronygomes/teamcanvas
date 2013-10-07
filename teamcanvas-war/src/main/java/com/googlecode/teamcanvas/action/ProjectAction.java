package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.User;
import com.googlecode.teamcanvas.service.ProjectService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Named
@RequestScoped
public class ProjectAction extends UserLoginTemplate{
    private List<Project> projects;

    @EJB
    private ProjectService projectService;

    @PostConstruct
    private void setUp(){
        loadUserFromSession();
        projects = projectService.findProjectByUser(getUser());
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
