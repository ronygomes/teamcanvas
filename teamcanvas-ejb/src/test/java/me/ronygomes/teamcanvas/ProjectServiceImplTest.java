package me.ronygomes.teamcanvas;

import jakarta.persistence.PersistenceException;
import me.ronygomes.teamcanvas.dao.PhaseDao;
import me.ronygomes.teamcanvas.dao.ProjectDao;
import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.ProjectService;
import me.ronygomes.teamcanvas.service.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProjectServiceImplTest {

    private ProjectDao projectDao;

    private PhaseDao phaseDao;

    private ProjectService projectService;

    @BeforeEach
    void setup() {
        this.projectDao = Mockito.mock(ProjectDao.class);
        this.phaseDao = Mockito.mock(PhaseDao.class);
        this.projectService = new ProjectServiceImpl(projectDao, phaseDao);
    }

    @Test
    void testSaveProject() {
        Project p = new Project();
        Assertions.assertTrue(projectService.saveProject(p));
        Mockito.verify(projectDao, Mockito.times(1)).saveProject(p);
    }

    @Test
    void testSaveProjectException() {
        Project p = new Project();
        Mockito.doThrow(PersistenceException.class).when(projectDao).saveProject(p);

        Assertions.assertFalse(projectService.saveProject(p));
        Mockito.verify(projectDao, Mockito.times(1)).saveProject(p);
    }

    @Test
    void testFindProjectByUser() {
        List<Project> expected = Arrays.asList(new Project());
        User u = new User();

        Mockito.when(projectDao.findProjectByUser(u)).thenReturn(expected);

        Assertions.assertIterableEquals(Collections.emptyList(), projectService.findProjectByUser(null));
        Assertions.assertSame(expected, projectService.findProjectByUser(u));

        Mockito.verify(projectDao, Mockito.times(1)).findProjectByUser(u);
    }

    @Test
    void testFindProjectByUserException() {
        User u = new User();

        Mockito.when(projectDao.findProjectByUser(u)).thenThrow(PersistenceException.class);

        Assertions.assertIterableEquals(Collections.emptyList(), projectService.findProjectByUser(u));
        Mockito.verify(projectDao, Mockito.times(1)).findProjectByUser(u);
    }

    @Test
    void testFindProjectById() {
        Project expected = new Project();
        Mockito.when(projectDao.findProjectById(1L)).thenReturn(expected);

        Assertions.assertSame(expected, projectService.findProjectById(1L));
        Mockito.verify(projectDao, Mockito.times(1)).findProjectById(1L);
    }

    @Test
    void testFindProjectByIdException() {
        Mockito.when(projectDao.findProjectById(1L)).thenThrow(PersistenceException.class);

        Assertions.assertNull(projectService.findProjectById(1L));
        Mockito.verify(projectDao, Mockito.times(1)).findProjectById(1L);
    }

    @Test
    void testUpdateProject() {
        Project p = new Project();
        Assertions.assertTrue(projectService.updateProject(p));
        Mockito.verify(projectDao, Mockito.times(1)).updateProject(p);
    }

    @Test
    void testUpdateProjectException() {
        Project p = new Project();
        Mockito.doThrow(PersistenceException.class).when(projectDao).updateProject(p);

        Assertions.assertFalse(projectService.updateProject(p));
        Mockito.verify(projectDao, Mockito.times(1)).updateProject(p);
    }

    @Test
    void testAddPhase() {
        Project project = new Project();
        Phase phase = Mockito.mock(Phase.class);

        projectService.addPhase(project, phase);

        Mockito.verify(phase, Mockito.times(1)).setProject(project);
        Mockito.verify(phaseDao, Mockito.times(1)).savePhase(phase);
        Mockito.verify(projectDao, Mockito.times(1)).updateProject(project);
    }

    @Test
    void testGetInProgressProject() {
        User u = new User();

        Project project1 = Mockito.mock(Project.class);
        Project project2 = Mockito.mock(Project.class);
        List<Project> expected = Arrays.asList(project1, project2);

        Mockito.when(projectDao.getInProgressProjects(u)).thenReturn(expected);
        List<Project> actual = projectService.getInProgressProject(u);

        Assertions.assertSame(expected, actual);
        Mockito.verify(project1, Mockito.times(1)).getPhases();
        Mockito.verify(project2, Mockito.times(1)).getPhases();
    }
}
