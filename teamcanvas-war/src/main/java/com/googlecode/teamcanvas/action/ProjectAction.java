package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.service.ProjectService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;


@Named
@RequestScoped
public class ProjectAction extends AppUtilTemplate {
    private List<Project> projects;

    @EJB
    private ProjectService projectService;

    @PostConstruct
    private void setUp() {
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
