package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.domain.User;
import com.googlecode.teamcanvas.service.TeamService;
import com.googlecode.teamcanvas.service.UserService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class AddTeamMemberAction extends UserSessionTemplate implements Serializable{
    private final Logger log = Logger.getLogger(AddTeamMemberAction.class);

    private Team team;
    private Map<String, String> parameterMap;
    private User member;

    @EJB
    private TeamService teamService;
    @EJB
    private UserService userService;

    @PostConstruct
    private void setUp(){
        loadUserFromSession();
        loadParamMap();
        if(teamIdUrlParamExists()){
            loadTeamFromDatabase(loadTeamIdFromUrlParam());
        }else {
            team = new Team();
            member = new User();
        }
    }

    private void loadParamMap() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        parameterMap = externalContext.getRequestParameterMap();
    }

    private boolean teamIdUrlParamExists() {
        String team_id = parameterMap.get("team_id");
        return team_id != null;
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

    private long loadTeamIdFromUrlParam() {
        String team_id = parameterMap.get("team_id");
        log.info("URL parameter in add_team_member.xhtml (team_id) " + team_id);
        return convertStringToLong(team_id);
    }

    private long convertStringToLong(String team_id) {
        try {
            return Long.parseLong(team_id);
        }catch (NumberFormatException n){
            log.info("Unable to convert " + team_id);
            return -1L;
        }
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
