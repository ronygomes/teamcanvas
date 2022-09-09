package me.ronygomes.teamcanvas.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import me.ronygomes.teamcanvas.domain.Task;

import java.util.List;

@Stateless
public class TaskDaoImpl implements TaskDao {

    private static final String FIND_TASK_BY_PHASE_ID = "SELECT t FROM Task t WHERE t.phase.id = :phaseId";

    @PersistenceContext(unitName = "teamcanvas")
    private EntityManager em;

    @Override
    public void saveTask(Task task) {
        task.setStatus(Task.Status.IN_PROGRESS);
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
