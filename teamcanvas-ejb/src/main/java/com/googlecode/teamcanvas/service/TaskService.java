package com.googlecode.teamcanvas.service;


import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.Task;

import java.util.List;

public interface TaskService {
    public boolean saveTask(Task task);

    public List<Task> findTaskByPhaseId(long phaseId);

    public Task findTaskById(long taskId);

    public void updateTask(Task task);

    public Project findProjectByTaskId(long taskId);
}
