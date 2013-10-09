package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.dao.PhaseDao;
import com.googlecode.teamcanvas.dao.TaskDao;
import com.googlecode.teamcanvas.domain.Phase;
import com.googlecode.teamcanvas.domain.Task;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class TaskServiceImpl implements TaskService {
    private final Logger log = Logger.getLogger(TaskServiceImpl.class);

    @EJB
    private TaskDao taskDao;


    @Override
    public boolean saveTask(Task task) {
        try{
            taskDao.saveTask(task);
            return true;
        }catch (PersistenceException e) {
            log.info("Unable to save task:" + task.getTaskTitle(), e);
        }
        return false;
    }

    @Override
    public List<Task> findTaskByPhaseId(long phaseId) {
       return taskDao.findTaskByPhaseId(phaseId);
    }
}
