package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.domain.Project;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class CreateProjectAction {
    private Project project;

    @PostConstruct
    private void setUp(){

    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
