package me.ronygomes.teamcanvas.dao;

import me.ronygomes.teamcanvas.domain.Phase;

public interface PhaseDao {

    void savePhase(Phase phase);

    Phase findPhaseById(long phaseId);
}
