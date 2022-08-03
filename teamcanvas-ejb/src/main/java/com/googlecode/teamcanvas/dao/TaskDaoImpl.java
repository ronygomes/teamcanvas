package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.Task;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class TaskDaoImpl implements TaskDao {

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    private final String FIND_TASK_BY_PHASE_ID = "SELECT t FROM Task t WHERE t.parentPhase.id = :phaseId";

    @Override
    public void saveTask(Task task) {
        task.setTaskStatus(Task.Status.IN_PROGRESS);
        em.persist(task);
    }

    public List<Task> findTaskByPhaseId(long phaseId) {
        TypedQuery<Task> query = em.createQuery(FIND_TASK_BY_PHASE_ID, Task.class);
        query.setParameter("phaseId", phaseId);
        return query.getResultList();

    }

    @Override
    public Task findTaskById(long taskId) {
        return em.find(Task.class, taskId);
    }

    @Override
    public void updateTask(Task taskToModify) {
        em.merge(taskToModify);
    }
}
