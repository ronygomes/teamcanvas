package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.service.TeamService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class TeamAction extends AppUtilTemplate {
    private final Logger log = Logger.getLogger(TeamAction.class);

    private List<Team> teams;

    @EJB
    private TeamService teamService;

    @PostConstruct
    public void setUp(){
        loadUserFromSession();
        teams = teamService.findTeamByOwner(getLoggedInUser());
        //log.info("" + teams.size() + " team found!");

        log.info(teamService);
        log.info("setUp : " + getLoggedInUser());
        //teams = new ArrayList<Team>();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
