package me.ronygomes.teamcanvas;

import jakarta.persistence.PersistenceException;
import me.ronygomes.teamcanvas.dao.PhaseDao;
import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.service.PhaseService;
import me.ronygomes.teamcanvas.service.PhaseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PhaseServiceImplTest {

    private PhaseDao phaseDao;

    private PhaseService phaseService;

    @BeforeEach
    void setup() {
        this.phaseDao = Mockito.mock(PhaseDao.class);
        this.phaseService = new PhaseServiceImpl(phaseDao);
    }

    @Test
    void testFindPhaseById() {
        Phase expected = Mockito.mock(Phase.class);
        Mockito.when(phaseDao.findPhaseById(1L)).thenReturn(expected);

        Phase actual = phaseService.findPhaseById(1L);
        Assertions.assertSame(expected, actual);

        Mockito.verify(phaseDao, Mockito.times(1)).findPhaseById(1L);
        Mockito.verify(expected, Mockito.times(1)).getTasks();
    }

    @Test
    void testFindPhaseByIdException() {

        Mockito.when(phaseDao.findPhaseById(1L)).thenThrow(PersistenceException.class);

        Phase actual = phaseService.findPhaseById(1L);
        Assertions.assertNull(actual);

        Mockito.verify(phaseDao, Mockito.times(1)).findPhaseById(1L);
    }
}
