package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.domain.User;
import com.googlecode.teamcanvas.service.TeamService;
import com.googlecode.teamcanvas.service.UserService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class AddTeamMemberAction extends AppUtilTemplate {
    private final Logger log = Logger.getLogger(AddTeamMemberAction.class);

    private Team team;
    private User member;

    @EJB
    private TeamService teamService;
    @EJB
    private UserService userService;

    private final String TEAM_ID_PARAM_KEY = "team_id";

    @PostConstruct
    private void setUp(){
        initUtilParams();
        if(paramExists(TEAM_ID_PARAM_KEY)){
            loadTeamFromDatabase(getParamValue(TEAM_ID_PARAM_KEY));
        }else {
            team = new Team();
            member = new User();
        }
    }

    private void loadTeamFromDatabase(long id) {
        team = teamService.findTeamById(id);
        log.info(team);
    }
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String appendMember(){
        log.info("Team: "  + team.getId());
        loadTeamFromDatabase(team.getId());
        log.info("Member: "  + member.getEmail());

        member = userService.findUserByEmail(member.getEmail());
        log.info(member.getEmail());

        team.getTeamMembers().add(member);

        teamService.updateTeam(team);

        return "add-team-member";
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }
}
