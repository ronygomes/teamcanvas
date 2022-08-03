package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.dao.TaskDao;
import com.googlecode.teamcanvas.domain.Phase;
import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.Task;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.PersistenceException;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class TaskServiceImpl implements TaskService {
    private final Logger log = Logger.getLogger(TaskServiceImpl.class);

    @EJB
    private TaskDao taskDao;


    @Override
    public boolean saveTask(Task task) {
        try {
            task.setTaskCreationTime(getCurrentTime());
            taskDao.saveTask(task);
            return true;
        } catch (PersistenceException e) {
            log.info("Unable to save task:" + task.getTaskTitle(), e);
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
        Phase phase = task.getParentPhase();
        Project project = phase.getParentProject();
        log.info("Project Id: " + (project == null ? "null" : project.getId()));
        return project;
    }

    private void updateTaskInstance(Task modifiedTask, Task oldTask) {
        modifiedTask.setTaskCreatedBy(oldTask.getTaskCreatedBy());
        modifiedTask.setTaskCreationTime(oldTask.getTaskCreationTime());
        modifiedTask.setParentPhase(oldTask.getParentPhase());
    }

    private Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }


}
