package com.googlecode.teamcanvas.domain;

import java.util.Date;

public class Project {
    private long id;
    private String projectTitle;
    private String projectDescription;
    private Date projectCreationTime;
    private Date projectLastModificationTime;
    private Date projectDueDate;
    private int projectCompletePercentage;
    private User createdBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Date getProjectCreationTime() {
        return projectCreationTime;
    }

    public void setProjectCreationTime(Date projectCreationTime) {
        this.projectCreationTime = projectCreationTime;
    }

    public Date getProjectLastModificationTime() {
        return projectLastModificationTime;
    }

    public void setProjectLastModificationTime(Date projectLastModificationTime) {
        this.projectLastModificationTime = projectLastModificationTime;
    }

    public Date getProjectDueDate() {
        return projectDueDate;
    }

    public void setProjectDueDate(Date projectDueDate) {
        this.projectDueDate = projectDueDate;
    }

    public int getProjectCompletePercentage() {
        return projectCompletePercentage;
    }

    public void setProjectCompletePercentage(int projectCompletePercentage) {
        this.projectCompletePercentage = projectCompletePercentage;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
