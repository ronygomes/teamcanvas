package me.ronygomes.teamcanvas.dao;

import me.ronygomes.teamcanvas.domain.Task;

import java.util.List;


public interface TaskDao {
    public void saveTask(Task task);

    public List<Task> findTaskByPhaseId(long phaseId);

    public Task findTaskById(long taskId);

    public void updateTask(Task taskToModify);
}
