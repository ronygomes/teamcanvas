package me.ronygomes.teamcanvas;

import jakarta.persistence.PersistenceException;
import me.ronygomes.teamcanvas.dao.TaskDao;
import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Task;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.TaskService;
import me.ronygomes.teamcanvas.service.TaskServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TaskServiceImplTest {

    private TaskDao taskDao;

    private TaskService taskService;

    @BeforeEach
    void setup() {
        this.taskDao = Mockito.mock(TaskDao.class);
        this.taskService = new TaskServiceImpl(taskDao);
    }

    @Test
    void testSaveTask() {
        Task task = new Task();
        Date now = new Date();

        ArgumentCaptor<Task> ac = ArgumentCaptor.forClass(Task.class);

        Assertions.assertTrue(taskService.saveTask(task, now));
        Mockito.verify(taskDao).saveTask(ac.capture());

        Assertions.assertSame(now, ac.getValue().getCreationDate());
    }

    @Test
    void testSaveTaskException() {
        Task task = new Task();
        Date now = new Date();

        Mockito.doThrow(PersistenceException.class).when(taskDao).saveTask(task);
        ArgumentCaptor<Task> ac = ArgumentCaptor.forClass(Task.class);

        Assertions.assertFalse(taskService.saveTask(task, now));
        Mockito.verify(taskDao).saveTask(ac.capture());

        Assertions.assertSame(now, ac.getValue().getCreationDate());
    }

    @Test
    void testFindTaskByPhaseId() {
        List<Task> tasks = Arrays.asList(new Task());

        Mockito.when(taskDao.findTaskByPhaseId(1L)).thenReturn(tasks);
        Assertions.assertSame(tasks, taskService.findTaskByPhaseId(1L));
        Mockito.verify(taskDao, Mockito.times(1)).findTaskByPhaseId(1L);
    }

    @Test
    void testFindTaskById() {
        Task task = new Task();

        Mockito.when(taskDao.findTaskById(1L)).thenReturn(task);
        Assertions.assertSame(task, taskService.findTaskById(1L));
        Mockito.verify(taskDao, Mockito.times(1)).findTaskById(1L);
    }

    @Test
    void testUpdateTask() {
        Task oldTask = new Task();
        oldTask.setCreator(new User());
        oldTask.setCreationDate(new Date());
        oldTask.setPhase(new Phase());

        Task modifiedTask = Mockito.spy(Task.class);

        Mockito.when(modifiedTask.getId()).thenReturn(1L);
        Mockito.when(taskDao.findTaskById(1)).thenReturn(oldTask);

        taskService.updateTask(modifiedTask);
        Mockito.verify(taskDao, Mockito.times(1)).updateTask(modifiedTask);

        Assertions.assertSame(oldTask.getCreator(), modifiedTask.getCreator());
        Assertions.assertSame(oldTask.getCreationDate(), modifiedTask.getCreationDate());
        Assertions.assertSame(oldTask.getPhase(), modifiedTask.getPhase());
    }

    @Test
    void testFindProjectByTaskId() {
        Project project = new Project();
        Task task = Mockito.mock(Task.class);
        Phase phase = Mockito.mock(Phase.class);

        Mockito.when(taskDao.findTaskById(1)).thenReturn(task);
        Mockito.when(task.getPhase()).thenReturn(phase);
        Mockito.when(phase.getProject()).thenReturn(project);

        Assertions.assertSame(project, taskService.findProjectByTaskId(1));
    }
}
