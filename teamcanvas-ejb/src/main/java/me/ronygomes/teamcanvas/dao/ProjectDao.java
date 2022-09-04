package me.ronygomes.teamcanvas.dao;

import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface ProjectDao {

    void saveProject(Project projectToSave);

    Project findProjectById(long projectId);

    List<Project> findProjectByUser(User user);

    void updateProject(Project project);

    List<Project> getInProgressProjects(User user);
}
