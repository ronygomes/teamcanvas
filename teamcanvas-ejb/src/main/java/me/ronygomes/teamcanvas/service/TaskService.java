package me.ronygomes.teamcanvas.service;


import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Task;

import java.util.List;

public interface TaskService {
    public boolean saveTask(Task task);

    public List<Task> findTaskByPhaseId(long phaseId);

    public Task findTaskById(long taskId);

    public void updateTask(Task task);

    public Project findProjectByTaskId(long taskId);
}
