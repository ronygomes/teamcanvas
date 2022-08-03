package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.Task;

import java.util.List;


public interface TaskDao {
    public void saveTask(Task task);

    public List<Task> findTaskByPhaseId(long phaseId);

    public Task findTaskById(long taskId);

    public void updateTask(Task taskToModify);
}
