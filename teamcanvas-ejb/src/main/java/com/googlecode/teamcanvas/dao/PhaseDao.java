package com.googlecode.teamcanvas.dao;


import com.googlecode.teamcanvas.domain.Phase;

public interface PhaseDao {
    public void savePhase(Phase phase);
    public Phase findPhaseById(long phaseId);
}
