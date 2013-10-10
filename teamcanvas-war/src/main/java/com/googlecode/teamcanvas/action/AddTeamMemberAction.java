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
import javax.faces.component.UIComponent;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class AddTeamMemberAction extends AppUtilTemplate {
    private final Logger log = Logger.getLogger(AddTeamMemberAction.class);

    private Team team;
    private User member;
    private UIComponent addMemberButton;

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
        log.info("New Member: "  + member.getEmail());


        //logTeam();
        log.info("Is team contain member: " + isTeamAlreadyContainMember(member));
        if(isTeamAlreadyContainMember(member)) {
            generateUserExistsErrorMessage();
        }else{
            saveMemberToDatabase();
        }

        return "add-team-member";
    }

    private void saveMemberToDatabase() {
        member = userService.findUserByEmail(member.getEmail());
        if(member != null){
            team.getTeamMembers().add(member);
            teamService.updateTeam(team);
        }else{
            addErrorMessage("User not Found", addMemberButton);
        }
    }

    private void logTeam() {
        List<User> teamMembers = team.getTeamMembers();
        String teamListString = "";
        for(User member : teamMembers){
            teamListString += member.getEmail() + ", ";
        }

        log.info("Old User: " + teamListString);
    }

    public UIComponent getAddMemberButton() {
        return addMemberButton;
    }

    public void setAddMemberButton(UIComponent addMemberButton) {
        this.addMemberButton = addMemberButton;
    }

    private void generateUserExistsErrorMessage() {
        addErrorMessage("User already in team", addMemberButton);
    }

    private boolean isTeamAlreadyContainMember(User member) {
        List<User> users = team.getTeamMembers();
        for(User user: users){
            if(user.getEmail().equals(member.getEmail()))
                return true;
        }
        return false;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }
}
