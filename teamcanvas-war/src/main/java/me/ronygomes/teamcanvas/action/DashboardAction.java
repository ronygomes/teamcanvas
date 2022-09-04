package me.ronygomes.teamcanvas.action;

import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.PhaseService;
import me.ronygomes.teamcanvas.service.ProjectService;
import me.ronygomes.teamcanvas.service.TeamService;
import me.ronygomes.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.log4j.Logger;

import java.util.List;

@Named
@RequestScoped
public class DashboardAction extends AppUtilTemplate {

    private Logger log = Logger.getLogger(DashboardAction.class);

    private List<Team> teams;
    private List<Project> projects;

    @EJB
    private ProjectService projectService;
    @EJB
    private TeamService teamService;
    @EJB
    private PhaseService phaseService;


    @PostConstruct
    public void setUp() {
        initUtilParams();
        loadProjectData();
        loadTeamInfo();
    }

    private void loadTeamInfo() {
        teams = teamService.findTeamByOwner(getLoggedInUser());
    }

    private void loadProjectData() {
        User user = getLoggedInUser();
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
