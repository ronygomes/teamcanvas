package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.domain.User;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TeamDaoImpl implements TeamDao {
    private final Logger log = Logger.getLogger(TeamDaoImpl.class);

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    private String FIND_TEAM_BY_USER = "SELECT t FROM Team t WHERE t.teamCreator.id = :userEmail";

    @Override
    public void saveTeam(Team team) {
        em.persist(team);
    }

    @Override
    public List<Team> findTeamByUser(User creatorOfTeam) {
        TypedQuery<Team> query = em.createQuery(FIND_TEAM_BY_USER, Team.class);
        query.setParameter("userEmail", creatorOfTeam.getEmail());
        log.info("Creator of Team: " + creatorOfTeam);
        List<Team> teams =  query.getResultList();
        log.info("" + (teams != null ? teams.size() : "No") + " team found!");
        return teams;
    }

    @Override
    public Team findTeamById(long teamId) {
        return em.find(Team.class, teamId);
    }

    @Override
    public void updateTeam(Team team) {
        em.merge(team);
    }

    @Override
    public void removeTeam(Team team) {
        em.remove(team);
    }

}
