package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.service.ProjectService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;


@Named
@RequestScoped
public class ProjectAction extends UserSessionTemplate {
    private List<Project> projects;

    @EJB
    private ProjectService projectService;

    @PostConstruct
    private void setUp(){
        loadUserFromSession();
        projects = projectService.findProjectByUser(getLoggedInUser());
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
