package me.ronygomes.teamcanvas.action;

import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.service.TeamService;
import me.ronygomes.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Named
@RequestScoped
public class TeamAction extends AppUtilTemplate {

    private final Logger log = LogManager.getLogger(TeamAction.class);

    private static final String TEAM_ID_URL_PARAM = "team_id";

    private List<Team> teams;
    private long teamId;

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
