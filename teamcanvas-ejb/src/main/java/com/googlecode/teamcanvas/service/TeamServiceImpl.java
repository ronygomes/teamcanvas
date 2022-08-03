package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.dao.TeamDao;
import com.googlecode.teamcanvas.dao.UserDao;
import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.domain.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.PersistenceException;
import org.apache.log4j.Logger;

import java.util.List;

@Stateless
public class TeamServiceImpl implements TeamService {
    private final Logger log = Logger.getLogger(TeamServiceImpl.class);

    @EJB
    private TeamDao teamDao;
    @EJB
    private UserDao userDao;

    @Override
    public List<Team> findTeamByOwner(User creatorOfTeam) {
        List<Team> teams = teamDao.findTeamByUser(creatorOfTeam);
        return teams;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean saveTeam(Team team) {
        try {
            teamDao.saveTeam(team);
            return true;
        } catch (PersistenceException e) {
            log.info("Unable to save team: " + team.getTeamName(), e);
            return false;
        }
    }

    @Override
    public Team findTeamById(long teamId) {
        Team team = null;
        try {
            team = teamDao.findTeamById(teamId);
            log.info("Team Found with id :" + teamId + " Team Name: " + team.getTeamName());
        } catch (PersistenceException e) {
            log.info("Team not found: " + teamId, e);
        }
        return team;
    }

    @Override
    public void updateTeam(Team team) {

        teamDao.updateTeam(team);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeTeam(long teamId) {
        Team team = teamDao.findTeamById(teamId);
        teamDao.removeTeam(team);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeMemberFromTeam(long teamId, String memberId) {
        Team team = teamDao.findTeamById(teamId);
        User user = userDao.findUserByEmail(memberId);
        List<User> teamMembers = team.getTeamMembers();
        if (teamMembers.contains(user)) {
            teamMembers.remove(user);
        }

        teamDao.updateTeam(team);
    }


}
