package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.User;

import java.util.List;

public interface ProjectDao {
    public void saveProject(Project projectToSave);
    public Project findProjectById(long projectId);
    public List<Project> findProjectByUser(User user);
    public void updateProject(Project project);
    public List<Project> getInProgressProjects(User user);
}
