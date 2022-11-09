package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.TeamService;
import me.ronygomes.teamcanvas.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

@Named
@RequestScoped
public class ManageTeamMemberAction {

    private final Logger log = LogManager.getLogger(ManageTeamMemberAction.class);

    private final String TEAM_ID_PARAM_KEY = "team_id";
    private final String USER_ID_PARAM_KEY = "user_id";

    private Team team;
    private User member;
    private UIComponent addMemberButton;

    @EJB
    private TeamService teamService;

    @EJB
    private UserService userService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    private void setUp() {
        if (applicationHelper.paramExists(facesContext, TEAM_ID_PARAM_KEY)) {
            loadTeamFromDatabase(applicationHelper.getLongParamValue(facesContext, TEAM_ID_PARAM_KEY));
            if (applicationHelper.paramExists(facesContext, USER_ID_PARAM_KEY)) {
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
            team.getMembers().add(member);
            teamService.updateTeam(team);
        } else {
            applicationHelper.addErrorMessage(facesContext, "User not Found", addMemberButton);
        }
    }

    public UIComponent getAddMemberButton() {
        return addMemberButton;
    }

    public void setAddMemberButton(UIComponent addMemberButton) {
        this.addMemberButton = addMemberButton;
    }

    private void generateUserExistsErrorMessage() {
        applicationHelper.addErrorMessage(facesContext, "User already in team", addMemberButton);
    }

    private boolean isUserAlreadyInTeam(User member) {
        List<User> users = team.getMembers();
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
        long teamId = applicationHelper.getLongParamValue(facesContext, TEAM_ID_PARAM_KEY);
        String memberId = applicationHelper.getStringParamValue(facesContext, USER_ID_PARAM_KEY);

        log.info("Team: " + teamId);
        log.info("User: " + memberId);

        teamService.removeMemberFromTeam(teamId, memberId);
        removeMemberFromAction(memberId);
    }

    private void removeMemberFromAction(String memberId) {
        List<User> users = team.getMembers();
        Iterator<User> it = users.iterator();

        while (it.hasNext()) {
            User user = it.next();
            if (user.getEmail().equals(memberId)) {
                it.remove();
            }
        }
        team.setMembers(users);
    }
}
