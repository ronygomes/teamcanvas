package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.TeamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Named
@RequestScoped
public class TeamAction {

    private final Logger log = LogManager.getLogger(TeamAction.class);

    private static final String TEAM_ID_URL_PARAM = "team_id";

    private List<Team> teams;
    private long teamId;

    @EJB
    private TeamService teamService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    public void setUp() {
        teams = teamService.findTeamByOwner(applicationHelper.getLoggedInUser(facesContext));

        log.info(teamService);
        log.info("setUp : " + applicationHelper.getLoggedInUser(facesContext));
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String deleteTeam() {

        log.info("Team Id: " + applicationHelper.getLongParamValue(facesContext, TEAM_ID_URL_PARAM));
        teamService.removeTeam(applicationHelper.getLongParamValue(facesContext, TEAM_ID_URL_PARAM));
        return "team.xhtml?faces-redirect=true";
    }
}
