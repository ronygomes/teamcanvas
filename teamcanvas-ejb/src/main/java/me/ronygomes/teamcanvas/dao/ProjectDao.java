package me.ronygomes.teamcanvas.dao;

import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface ProjectDao {
    public void saveProject(Project projectToSave);

    public Project findProjectById(long projectId);

    public List<Project> findProjectByUser(User user);

    public void updateProject(Project project);

    public List<Project> getInProgressProjects(User user);
}
