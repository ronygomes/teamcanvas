package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.ProjectService;

import java.util.List;

@Named
@RequestScoped
public class ProjectAction {

    private List<Project> projects;

    @EJB
    private ProjectService projectService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    private void setUp() {
        projects = projectService.findProjectByUser(applicationHelper.getLoggedInUser(facesContext));
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
