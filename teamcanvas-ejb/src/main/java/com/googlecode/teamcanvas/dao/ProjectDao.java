package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.Project;

public interface ProjectDao {
    public void saveProject(Project projectToSave);
    public Project findProjectById(Long projectId);

}
