package me.ronygomes.teamcanvas.action;

import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.TeamService;
import me.ronygomes.teamcanvas.service.UserService;
import me.ronygomes.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.inject.Named;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

@Named
@RequestScoped
public class ManageTeamMemberAction extends AppUtilTemplate {
    private final Logger log = Logger.getLogger(ManageTeamMemberAction.class);

    private Team team;
    private User member;
    private UIComponent addMemberButton;

    @EJB
    private TeamService teamService;
    @EJB
    private UserService userService;

    private final String TEAM_ID_PARAM_KEY = "team_id";
    private final String USER_ID_PARAM_KEY = "user_id";

    @PostConstruct
    private void setUp() {
        initUtilParams();
        if (paramExists(TEAM_ID_PARAM_KEY)) {
            loadTeamFromDatabase(getLongParamValue(TEAM_ID_PARAM_KEY));
            if (paramExists(USER_ID_PARAM_KEY)) {
                deleteMember();
            }
        } else {
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

    public String appendMember() {
        log.info("Team: " + team.getId());
        loadTeamFromDatabase(team.getId());
        log.info("New Member: " + member.getEmail());


        //logTeams();
        log.info("Is team contain member: " + isUserAlreadyInTeam(member));
        if (isUserAlreadyInTeam(member)) {
            generateUserExistsErrorMessage();
        } else {
            saveMemberToDatabase();
        }

        return "add-team-member";
    }

    private void saveMemberToDatabase() {
        member = userService.findUserByEmail(member.getEmail());
        if (member != null) {
            team.getTeamMembers().add(member);
            teamService.updateTeam(team);
        } else {
            addErrorMessage("User not Found", addMemberButton);
        }
    }

    private void logTeams() {
        List<User> teamMembers = team.getTeamMembers();
        String teamListString = "";
        for (User member : teamMembers) {
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

    private boolean isUserAlreadyInTeam(User member) {
        List<User> users = team.getTeamMembers();
        for (User user : users) {
            if (user.getEmail().equals(member.getEmail()))
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

    public void deleteMember() {
        long teamId = getLongParamValue(TEAM_ID_PARAM_KEY);
        String memberId = getStringParamValue(USER_ID_PARAM_KEY);

        log.info("Team: " + teamId);
        log.info("User: " + memberId);

        teamService.removeMemberFromTeam(teamId, memberId);
        removeMemberFromAction(memberId);
    }

    private void removeMemberFromAction(String memberId) {
        List<User> users = team.getTeamMembers();
        Iterator<User> it = users.iterator();

        while (it.hasNext()) {
            User user = it.next();
            if (user.getEmail().equals(memberId)) {
                it.remove();
            }
        }
        team.setTeamMembers(users);
    }


}
