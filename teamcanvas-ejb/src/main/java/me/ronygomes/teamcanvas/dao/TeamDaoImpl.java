package me.ronygomes.teamcanvas.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;
import org.apache.log4j.Logger;

import java.util.List;

@Stateless
public class TeamDaoImpl implements TeamDao {

    private final Logger log = Logger.getLogger(TeamDaoImpl.class);

    private static final String FIND_TEAM_BY_USER = "SELECT t FROM Team t WHERE t.creator.email = :userEmail";

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    @Override
    public void saveTeam(Team team) {
        em.persist(team);
    }

    @Override
    public List<Team> findTeamByUser(User creatorOfTeam) {
        TypedQuery<Team> query = em.createQuery(FIND_TEAM_BY_USER, Team.class);
        query.setParameter("userEmail", creatorOfTeam.getEmail());
        log.info("Creator of Team: " + creatorOfTeam);
        List<Team> teams = query.getResultList();
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
