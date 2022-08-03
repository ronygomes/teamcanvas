package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.service.TeamService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.log4j.Logger;

import java.util.List;

@Named
@RequestScoped
public class TeamAction extends AppUtilTemplate {
    private final Logger log = Logger.getLogger(TeamAction.class);

    private List<Team> teams;
    private long teamId;

    private static String TEAM_ID_URL_PARAM = "team_id";

    @EJB
    private TeamService teamService;

    @PostConstruct
    public void setUp() {
        initUtilParams();
        teams = teamService.findTeamByOwner(getLoggedInUser());

        log.info(teamService);
        log.info("setUp : " + getLoggedInUser());
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

        log.info("Team Id: " + getLongParamValue(TEAM_ID_URL_PARAM));
        teamService.removeTeam(getLongParamValue(TEAM_ID_URL_PARAM));
        return "team.xhtml?faces-redirect=true";
    }
}
