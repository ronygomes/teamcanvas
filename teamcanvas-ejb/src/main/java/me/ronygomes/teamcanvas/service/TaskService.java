package me.ronygomes.teamcanvas.service;

import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Task;

import java.util.List;

public interface TaskService {

    boolean saveTask(Task task);

    List<Task> findTaskByPhaseId(long phaseId);

    Task findTaskById(long taskId);

    void updateTask(Task task);

    Project findProjectByTaskId(long taskId);
}
