package me.ronygomes.teamcanvas.action;

import jakarta.faces.context.FacesContext;
import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Task;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.PhaseService;
import me.ronygomes.teamcanvas.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AddTaskActionTest {
    private static final String REDIRECT_URL = "project-details.xhtml?project_id=10&faces-redirect=true";

    @Mock
    private FacesContext facesContext;

    @Mock
    private ApplicationHelper applicationHelper;

    @Mock
    private PhaseService phaseService;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private AddTaskAction addTaskAction;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewPhaseSetUp() {
        Assertions.assertNull(addTaskAction.getPhase());
        Assertions.assertNull(addTaskAction.getTask());

        addTaskAction.setUp();

        Assertions.assertNotNull(addTaskAction.getPhase());
        Assertions.assertNotNull(addTaskAction.getTask());

        Mockito.verify(applicationHelper).paramExists(facesContext, "phase_id");
    }

    @Test
    void testExistingPhaseSetUp() {
        Mockito.when(applicationHelper.paramExists(facesContext, "phase_id")).thenReturn(true);
        Mockito.when(applicationHelper.getLongParamValue(facesContext, "phase_id")).thenReturn(10L);

        Phase dbPhase = new Phase();
        Mockito.when(phaseService.findPhaseById(10L)).thenReturn(dbPhase);

        Assertions.assertNull(addTaskAction.getPhase());
        Assertions.assertNull(addTaskAction.getTask());

        addTaskAction.setUp();

        Assertions.assertSame(dbPhase, addTaskAction.getPhase());
        Assertions.assertNotNull(addTaskAction.getTask());

        Mockito.verify(applicationHelper).paramExists(facesContext, "phase_id");
        Mockito.verify(applicationHelper).getLongParamValue(facesContext, "phase_id");
        Mockito.verify(phaseService).findPhaseById(10L);
    }

    @Test
    void testAddTaskToPhase() {
        Task task = Mockito.mock(Task.class);
        User user = new User();

        Project project = new Project();
        project.setId(10L);

        Mockito.when(applicationHelper.paramExists(facesContext, "phase_id")).thenReturn(true);
        Mockito.when(applicationHelper.getLongParamValue(facesContext, "phase_id")).thenReturn(10L);
        Mockito.when(applicationHelper.getLoggedInUser(facesContext)).thenReturn(user);

        Phase dbPhase = new Phase();
        dbPhase.setId(10L);
        dbPhase.setProject(project);

        Mockito.when(phaseService.findPhaseById(10L)).thenReturn(dbPhase);

        addTaskAction.setUp();
        addTaskAction.setTask(task);

        String redirectUrl = addTaskAction.addTaskToPhase();

        Mockito.verify(phaseService, Mockito.times(2)).findPhaseById(10L);
        Mockito.verify(task).setPhase(addTaskAction.getPhase());
        Mockito.verify(task).setCreator(user);
        Mockito.verify(taskService).saveTask(Mockito.eq(task), Mockito.any());

        Assertions.assertEquals(REDIRECT_URL, redirectUrl);
    }
}
