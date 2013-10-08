package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.dao.TeamDao;
import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.domain.User;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TeamServiceImpl implements TeamService {
    private final Logger log = Logger.getLogger(TeamServiceImpl.class);

    @EJB
    private TeamDao teamDao;

    @Override
    public List<Team> findTeamByOwner(User creatorOfTeam) {
        List<Team> teams = teamDao.findTeamByUser(creatorOfTeam);
        return teams;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean saveTeam(Team team) {
        try{
            teamDao.saveTeam(team);
            return true;
        }catch (PersistenceException e){
            log.info("Unable to save team: " + team.getTeamName(), e);
            return false;
        }
    }

    @Override
    public Team findTeamById(long teamId) {
        Team team = null;
        try{
            team = teamDao.findTeamById(teamId);
            log.info("Team Found with id :" + teamId + " Team Name: " + team.getTeamName());
        }catch (PersistenceException e){
            log.info("Team not found: " + teamId, e);
        }
        return team;
    }

    @Override
    public void updateTeam(Team team) {
        teamDao.updateTeam(team);
    }


}
