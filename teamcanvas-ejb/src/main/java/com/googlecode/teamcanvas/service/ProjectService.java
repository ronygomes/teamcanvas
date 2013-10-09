package com.googlecode.teamcanvas.service;


import com.googlecode.teamcanvas.domain.Phase;
import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.User;

import java.util.List;

public interface ProjectService {
    public boolean saveProject(Project projectToSave);
    public List<Project> findProjectByUser(User user);
    public Project findProjectById(long id);
    public boolean updateProject(Project project);
    public void addPhase(Project project, Phase phase);
}
