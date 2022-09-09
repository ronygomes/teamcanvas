package me.ronygomes.teamcanvas.service;

import me.ronygomes.teamcanvas.dao.TaskDao;
import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Task;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class TaskServiceImpl implements TaskService {

    private final Logger log = LogManager.getLogger(TaskServiceImpl.class);

    @EJB
    private TaskDao taskDao;

    @Override
    public boolean saveTask(Task task) {

        try {
            task.setCreationDate(getCurrentTime());
            taskDao.saveTask(task);
            return true;
        } catch (PersistenceException e) {
            log.info("Unable to save task:" + task.getTitle(), e);
        }

        return false;
    }

    @Override
    public List<Task> findTaskByPhaseId(long phaseId) {
        return taskDao.findTaskByPhaseId(phaseId);
    }

    @Override
    public Task findTaskById(long taskId) {
        return taskDao.findTaskById(taskId);
    }

    @Override
    public void updateTask(Task modifiedTask) {
        Task oldTask = taskDao.findTaskById(modifiedTask.getId());
        updateTaskInstance(modifiedTask, oldTask);
        taskDao.updateTask(modifiedTask);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Project findProjectByTaskId(long taskId) {
        Task task = taskDao.findTaskById(taskId);
        Phase phase = task.getPhase();
        Project project = phase.getProject();
        log.info("Project Id: " + (project == null ? "null" : project.getId()));
        return project;
    }

    private void updateTaskInstance(Task modifiedTask, Task oldTask) {
        modifiedTask.setCreator(oldTask.getCreator());
        modifiedTask.setCreationDate(oldTask.getCreationDate());
        modifiedTask.setPhase(oldTask.getPhase());
    }

    private Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }
}
