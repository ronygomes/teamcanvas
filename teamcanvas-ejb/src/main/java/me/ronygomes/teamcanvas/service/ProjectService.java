package me.ronygomes.teamcanvas.service;


import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface ProjectService {
    public boolean saveProject(Project projectToSave);

    public List<Project> findProjectByUser(User user);

    public Project findProjectById(long id);

    public boolean updateProject(Project project);

    public void addPhase(Project project, Phase phase);

    public List<Project> getInProgressProject(User user);
}
