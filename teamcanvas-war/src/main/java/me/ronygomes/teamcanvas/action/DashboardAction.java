package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.PhaseService;
import me.ronygomes.teamcanvas.service.ProjectService;
import me.ronygomes.teamcanvas.service.TeamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Named
@RequestScoped
public class DashboardAction {

    private final Logger log = LogManager.getLogger(DashboardAction.class);

    private List<Team> teams;
    private List<Project> projects;

    @EJB
    private ProjectService projectService;

    @EJB
    private TeamService teamService;

    @EJB
    private PhaseService phaseService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    public void setUp() {
        loadProjectData();
        loadTeamInfo();
    }

    private void loadTeamInfo() {
        teams = teamService.findTeamByOwner(applicationHelper.getLoggedInUser(facesContext));
    }

    private void loadProjectData() {
        User user = applicationHelper.getLoggedInUser(facesContext);
        projects = projectService.getInProgressProject(user);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
