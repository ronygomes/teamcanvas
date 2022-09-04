package me.ronygomes.teamcanvas.dao;

import me.ronygomes.teamcanvas.domain.Task;

import java.util.List;

public interface TaskDao {

    void saveTask(Task task);

    List<Task> findTaskByPhaseId(long phaseId);

    Task findTaskById(long taskId);

    void updateTask(Task taskToModify);
}
