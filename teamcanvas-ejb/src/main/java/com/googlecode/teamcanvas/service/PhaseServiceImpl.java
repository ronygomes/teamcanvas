package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.dao.PhaseDao;
import com.googlecode.teamcanvas.domain.Phase;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

@Stateless
public class PhaseServiceImpl implements PhaseService {
    private final Logger log = Logger.getLogger(PhaseServiceImpl.class);

    @EJB
    private PhaseDao phaseDao;

    @Override
    public Phase findPhaseById(long id) {
        Phase phase = null;
        try{
            phase = phaseDao.findPhaseById(id);
            phase.getTasks();
        }catch(PersistenceException e){
            log.info("Phase not found: phaseId -> "  + id , e);
        }
        return phase;
    }
}
