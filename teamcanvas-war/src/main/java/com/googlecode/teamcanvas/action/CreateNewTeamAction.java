package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.service.TeamService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

@Named
@RequestScoped
public class CreateNewTeamAction extends AppUtilTemplate {
    private final Logger log = Logger.getLogger(CreateNewTeamAction.class);

    private Team team;

    @EJB
    private TeamService teamService;

    @PostConstruct
    private void setUp() {
        initializeTeam();
        loadUserFromSession();
    }

    private void initializeTeam() {
        if (team == null) {
            team = new Team();
        }
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String createTeam() {
        String outcome = "create-new-team";
        if (isLoggedInUserFound()) {
            configureTeam();
            if (saveTeam()) {
                outcome = "team.xhtml?faces-redirect=true";
            }
        }
        return outcome;
    }

    private boolean saveTeam() {
        return teamService.saveTeam(team);
    }

    private void configureTeam() {
        setTeamCreator();
        setTeamCreationTime();
    }

    private void setTeamCreationTime() {
        Date currentTime = Calendar.getInstance().getTime();
        team.setTeamCreationTime(currentTime);
    }

    private void setTeamCreator() {
        team.setTeamCreator(getLoggedInUser());
    }


}
