package me.ronygomes.teamcanvas.service;

import me.ronygomes.teamcanvas.dao.PhaseDao;
import me.ronygomes.teamcanvas.domain.Phase;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
public class PhaseServiceImpl implements PhaseService {

    private final Logger log = LogManager.getLogger(PhaseServiceImpl.class);

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
