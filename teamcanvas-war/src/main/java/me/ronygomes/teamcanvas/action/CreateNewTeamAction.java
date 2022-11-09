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

import java.util.Calendar;
import java.util.Date;

@Named
@RequestScoped
public class CreateNewTeamAction {

    private final Logger log = LogManager.getLogger(CreateNewTeamAction.class);

    private Team team;

    @EJB
    private TeamService teamService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    private void setUp() {
        initializeTeam();
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
        if (applicationHelper.isLoggedInUserFound(facesContext)) {
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
        team.setCreationDate(currentTime);
    }

    private void setTeamCreator() {
        team.setCreator(applicationHelper.getLoggedInUser(facesContext));
    }
}
