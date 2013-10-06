package com.googlecode.teamcanvas.domain;

import javax.persistence.Id;
import java.util.Date;

public class Task {
    @Id
    private long id;
    private String taskTitle;
    private String taskDescription;
    private int completePercentage;
    private int taskPriority;
    private Date taskCreationTime;
    private Date taskLastModifiedTime;
    private boolean isPrivate;
    private Date taskDueDate;
    private Phase parentPhase;
    private User taskCreatedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getCompletePercentage() {
        return completePercentage;
    }

    public void setCompletePercentage(int completePercentage) {
        this.completePercentage = completePercentage;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Date getTaskCreationTime() {
        return taskCreationTime;
    }

    public void setTaskCreationTime(Date taskCreationTime) {
        this.taskCreationTime = taskCreationTime;
    }

    public Date getTaskLastModifiedTime() {
        return taskLastModifiedTime;
    }

    public void setTaskLastModifiedTime(Date taskLastModifiedTime) {
        this.taskLastModifiedTime = taskLastModifiedTime;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Date getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(Date taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public Phase getParentPhase() {
        return parentPhase;
    }

    public void setParentPhase(Phase parentPhase) {
        this.parentPhase = parentPhase;
    }

    public User getTaskCreatedBy() {
        return taskCreatedBy;
    }

    public void setTaskCreatedBy(User taskCreatedBy) {
        this.taskCreatedBy = taskCreatedBy;
    }
}
