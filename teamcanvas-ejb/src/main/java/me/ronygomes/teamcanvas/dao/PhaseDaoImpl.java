package me.ronygomes.teamcanvas.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.ronygomes.teamcanvas.domain.Phase;
import org.apache.log4j.Logger;

@Stateless
public class PhaseDaoImpl implements PhaseDao {

    private final Logger log = Logger.getLogger(PhaseDaoImpl.class);

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    @Override
    public void savePhase(Phase phase) {
        em.persist(phase);
    }

    @Override
    public Phase findPhaseById(long phaseId) {
        return em.find(Phase.class, phaseId);
    }
}
