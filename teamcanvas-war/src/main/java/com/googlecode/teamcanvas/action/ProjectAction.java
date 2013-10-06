package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class ProjectAction {
    private List<Project> projects;
    private User user;

    @PostConstruct
    private void setUp(){
        projects = new ArrayList<Project>();
        Project p1 = new Project();
        p1.setProjectTitle("Project #1");
        p1.setProjectDescription("Project Description#1");

        Project p2 = new Project();
        p2.setProjectTitle("Project 2");
        p2.setProjectDescription("Project Description #2");
        projects.add(p1);
        projects.add(p2);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
