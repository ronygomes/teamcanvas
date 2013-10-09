package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.Phase;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
