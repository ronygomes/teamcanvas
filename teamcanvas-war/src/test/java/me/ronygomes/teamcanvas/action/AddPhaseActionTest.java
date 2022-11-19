package me.ronygomes.teamcanvas.action;

import jakarta.faces.context.FacesContext;
import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.ProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class AddPhaseActionTest {

    private static final String REDIRECT_URL = "project-details.xhtml?project_id=10&faces-redirect=true";

    @Mock
    private FacesContext facesContext;

    @Mock
    private ProjectService projectService;

    @Mock
    private ApplicationHelper applicationHelper;

    @Mock
    private Phase phase;

    @InjectMocks
    private AddPhaseAction addPhaseAction;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetUpNewProject() {
        Project p0 = new Project();
        p0.setId(10);

        addPhaseAction.setProject(p0);

        Mockito.when(applicationHelper.paramExists(facesContext, "project_id")).thenReturn(false);

        addPhaseAction.setUp();

        Assertions.assertEquals(0, addPhaseAction.getProject().getId());
        Assertions.assertNotSame(p0, addPhaseAction.getProject());
        Mockito.verifyNoInteractions(projectService);
    }

    @Test
    void testSetUpExistingProject() {
        Project p0 = new Project();
        p0.setId(10);

        addPhaseAction.setProject(p0);

        Mockito.when(applicationHelper.paramExists(facesContext, "project_id")).thenReturn(true);
        Mockito.when(applicationHelper.getLongParamValue(facesContext, "project_id")).thenReturn(10L);
        Mockito.when(projectService.findProjectById(10)).thenReturn(p0);

        addPhaseAction.setUp();

        Assertions.assertEquals(10, addPhaseAction.getProject().getId());
        Assertions.assertSame(p0, addPhaseAction.getProject());
        Mockito.verify(projectService).findProjectById(10);
    }

    @Test
    void testAddActionToProject() {
        Project p = Mockito.mock(Project.class);
        List<Phase> phases = Mockito.mock(List.class);
        addPhaseAction.setProject(p);

        Mockito.when(p.getId()).thenReturn(10L);
        Mockito.when(p.getPhases()).thenReturn(phases);
        Mockito.when(projectService.findProjectById(10)).thenReturn(p);

        String redirectUrl = addPhaseAction.addPhaseToProject();

        Assertions.assertEquals(REDIRECT_URL, redirectUrl);
        Mockito.verify(projectService).addPhase(p, phase);
        Mockito.verify(phases).add(phase);
    }
}
