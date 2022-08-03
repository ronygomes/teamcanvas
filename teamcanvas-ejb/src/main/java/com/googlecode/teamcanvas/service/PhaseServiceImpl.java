package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.dao.PhaseDao;
import com.googlecode.teamcanvas.domain.Phase;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceException;
import org.apache.log4j.Logger;

@Stateless
public class PhaseServiceImpl implements PhaseService {
    private final Logger log = Logger.getLogger(PhaseServiceImpl.class);

    @EJB
    private PhaseDao phaseDao;

    @Override
    public Phase findPhaseById(long id) {
        Phase phase = null;
        try {
            phase = phaseDao.findPhaseById(id);
            phase.getTasks();
        } catch (PersistenceException e) {
            log.info("Phase not found: phaseId -> " + id, e);
        }
        return phase;
    }
}
