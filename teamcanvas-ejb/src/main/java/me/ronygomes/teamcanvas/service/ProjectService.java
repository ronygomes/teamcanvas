package me.ronygomes.teamcanvas.service;

import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface ProjectService {

    boolean saveProject(Project projectToSave);

    List<Project> findProjectByUser(User user);

    Project findProjectById(long id);

    boolean updateProject(Project project);

    void addPhase(Project project, Phase phase);

    List<Project> getInProgressProject(User user);
}
