package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TaskDaoImpl implements TaskDao {

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    private final String FIND_TASK_BY_PHASE_ID = "SELECT t FROM Task t WHERE t.parentPhase.id = :phaseId";
    @Override
    public void saveTask(Task task) {
        em.persist(task);
    }

    public List<Task> findTaskByPhaseId(long phaseId){
        TypedQuery<Task> query = em.createQuery(FIND_TASK_BY_PHASE_ID, Task.class);
        query.setParameter("phaseId", phaseId);
        return query.getResultList();

    }
}
