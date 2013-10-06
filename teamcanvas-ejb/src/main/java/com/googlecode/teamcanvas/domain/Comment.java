package com.googlecode.teamcanvas.domain;

import java.util.Date;

public class Comment {
    private long id;
    private Project parentProject;
    private User commentCreatedBy;
    private Date commentCreationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Project getParentProject() {
        return parentProject;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public User getCommentCreatedBy() {
        return commentCreatedBy;
    }

    public void setCommentCreatedBy(User commentCreatedBy) {
        this.commentCreatedBy = commentCreatedBy;
    }

    public Date getCommentCreationTime() {
        return commentCreationTime;
    }

    public void setCommentCreationTime(Date commentCreationTime) {
        this.commentCreationTime = commentCreationTime;
    }
}
