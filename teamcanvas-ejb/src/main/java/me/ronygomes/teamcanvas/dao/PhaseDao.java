package me.ronygomes.teamcanvas.dao;


import me.ronygomes.teamcanvas.domain.Phase;

public interface PhaseDao {
    public void savePhase(Phase phase);

    public Phase findPhaseById(long phaseId);
}
